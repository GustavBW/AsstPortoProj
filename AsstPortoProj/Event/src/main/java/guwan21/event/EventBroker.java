package guwan21.event;

import guwan21.common.events.Event;
import guwan21.common.events.EventQueryParameters;
import guwan21.common.events.IEventBroker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class EventBroker implements IEventBroker {

    /**
     * Events by their emitter's class
     */
    private final Map<Class<?>, Set<Event<?>>> emitterClassEventMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Set<Function<Event<?>,Boolean>>> emitterClassSubscriberMap = new ConcurrentHashMap<>();
    /**
     * Events by their target's class
     */
    private final Map<Class<?>, Set<Event<?>>> targetClassEventMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Set<Function<Event<?>,Boolean>>> targetClassSubscriberMap = new ConcurrentHashMap<>();

    /**
     * Events by their Event.Target enum value
     */
    private final Map<Event.Target, Set<Event<?>>> targetEventMap = new ConcurrentHashMap<>();
    private final Map<Event.Target, Set<Function<Event<?>,Boolean>>> targetSubscriberMap = new ConcurrentHashMap<>();
    /**
     * Events by their Event.Type enum value
     */
    private final Map<Event.Type, Set<Event<?>>> typeEventMap = new ConcurrentHashMap<>();
    private final Map<Event.Type, Set<Function<Event<?>,Boolean>>> typeSubscriberMap = new ConcurrentHashMap<>();
    /**
     * Events by their Event.Category enum value
     */
    private final Map<Event.Category, Set<Event<?>>> categoryEventMap = new ConcurrentHashMap<>();
    private final Map<Event.Category, Set<Function<Event<?>,Boolean>>> categorySubscriberMap = new ConcurrentHashMap<>();
    /**
     * Events by their name
     */
    private final Map<String,Set<Event<?>>> nameEventMap = new ConcurrentHashMap<>();
    private final Map<String,Set<Function<Event<?>,Boolean>>> nameSubscribersMap = new ConcurrentHashMap<>();

    private final Set<Event<?>> allCurrentEvents = new HashSet<>();

    public EventBroker(){
        //All enumerations can be pre-populated
        Arrays.stream(Event.Target.values()).forEach(
                target -> {
                    targetEventMap.put(target, new HashSet<>());
                    targetSubscriberMap.put(target, new HashSet<>());
                }
        );
        Arrays.stream(Event.Type.values()).forEach(
                type -> {
                    typeEventMap.put(type, new HashSet<>());
                    typeSubscriberMap.put(type, new HashSet<>());
                }
        );
        Arrays.stream(Event.Category.values()).forEach(
                category -> {
                    categoryEventMap.put(category, new HashSet<>());
                    categorySubscriberMap.put(category, new HashSet<>());
                }
        );
        //Event.ANY_CLASS is not an enumerable but is a known value which can be pre-populated
        targetClassEventMap.put(Event.ANY_CLASS, new HashSet<>());
        emitterClassEventMap.put(Event.ANY_CLASS, new HashSet<>());

        emitterClassSubscriberMap.put(Event.ANY_CLASS, new HashSet<>());
        targetClassSubscriberMap.put(Event.ANY_CLASS, new HashSet<>());

        //Event.UNNAMED is not an enumerable but is a known value which can be pre-populated
        nameEventMap.put(Event.ANY_NAME, new HashSet<>());
        nameSubscribersMap.put(Event.ANY_NAME, new HashSet<>());
    }

    @Override
    public Function<Event<?>,Boolean> subscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params){
        emitterClassSubscriberMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>()).add(callback);
        targetClassSubscriberMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>()).add(callback);
        nameSubscribersMap.computeIfAbsent(params.name(), k -> new HashSet<>()).add(callback);

        targetSubscriberMap.get(params.target()).add(callback);
        typeSubscriberMap.get(params.type()).add(callback);
        categorySubscriberMap.get(params.category()).add(callback);

        return callback;
    }

    @Override
    public void unsubscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params){
        emitterClassSubscriberMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>()).remove(callback);
        targetClassSubscriberMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>()).remove(callback);
        nameSubscribersMap.computeIfAbsent(params.name(), k -> new HashSet<>()).remove(callback);

        typeSubscriberMap.get(params.type()).remove(callback);
        targetSubscriberMap.get(params.target()).remove(callback);
        categorySubscriberMap.get(params.category()).remove(callback);
    }

    private Collection<Function<Event<?>,Boolean>> querySubscribers(EventQueryParameters params){
        Set<Function<Event<?>,Boolean>> byEmittorClass= emitterClassSubscriberMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>()); //possibly unpopulated
        byEmittorClass.addAll(emitterClassSubscriberMap.get(Event.ANY_CLASS)); //pre-populated in constructor
        Set<Function<Event<?>,Boolean>> byTargetClass = targetClassSubscriberMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>());
        byTargetClass.addAll(targetClassSubscriberMap.get(Event.ANY_CLASS));
        Set<Function<Event<?>,Boolean>> byName = nameSubscribersMap.computeIfAbsent(params.name(), k -> new HashSet<>());
        byName.addAll(nameSubscribersMap.computeIfAbsent(Event.ANY_NAME, k -> new HashSet<>()));

        Set<Function<Event<?>,Boolean>> byTarget      = targetSubscriberMap.get(params.target());
        byTarget.addAll(targetSubscriberMap.get(Event.Target.ANY));
        Set<Function<Event<?>,Boolean>> byType        = typeSubscriberMap.get(params.type());
        byType.addAll(typeSubscriberMap.get(Event.Type.ANY));
        Set<Function<Event<?>,Boolean>> byCategory    = categorySubscriberMap.get(params.category());
        byCategory.addAll(categorySubscriberMap.get(Event.Category.ANY));

        return intersection(List.of(byEmittorClass, byTargetClass, byTarget, byType, byCategory, byName));
    }

    /**
     * Queries subscribers, returns true if any subscriber tried to consume the event.
     * @param event event to query subscribers for
     * @return true if consumed
     */
    private boolean evaluateSubscribers(Event<?> event){
        EventQueryParameters params = new EventQueryParameters(event.getSourceType(),event.getTargetType(),event.getTarget(),event.getType(),event.getCategory(),event.getName());
        Collection<Function<Event<?>,Boolean>> subscribers = querySubscribers(params);
        boolean consumed = false;

        for(Function<Event<?>,Boolean> subscriber : subscribers){
            if(subscriber.apply(event)){
                consumed = true;
            }
        }

        return consumed && event.getType() == Event.Type.INSTANT;
    }

    /**
     * If any parameter of the query is null, only null.
     * @return any events that matches all parameters.
     */
    @Override
    public Collection<Event<?>> querySpecific(EventQueryParameters params) {
        Set<Event<?>> byEmittorClass= emitterClassEventMap.computeIfAbsent(params.emittorClass(), k-> new HashSet<>());
        Set<Event<?>> byTargetClass = targetClassEventMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>());
        Set<Event<?>> byName = nameEventMap.computeIfAbsent(params.name(), k -> new HashSet<>());

        Set<Event<?>> byTarget      = targetEventMap.get(params.target());
        Set<Event<?>> byType        = typeEventMap.get(params.type());
        Set<Event<?>> byCategory    = categoryEventMap.get(params.category());

        return intersection(List.of(byEmittorClass, byTargetClass, byTarget, byType, byCategory, byName));
    }
    /**
     * If any parameter of the query is null, any will match.
     * @param params Query Parameters
     * @return any events that matches all parameters.
     */
    @Override
    public Collection<Event<?>> queryAny(EventQueryParameters params) {
        Set<Event<?>> byEmittorClass= params.emittorClass() == Event.ANY_CLASS  ? allCurrentEvents : emitterClassEventMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>());
        Set<Event<?>> byTargetClass = params.targetClass() == Event.ANY_CLASS   ? allCurrentEvents : targetClassEventMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>());
        Set<Event<?>> byName        = params.name().equals(Event.ANY_NAME)       ? allCurrentEvents : nameEventMap.computeIfAbsent(params.name(), k -> new HashSet<>());

        Set<Event<?>> byTarget      = params.target() == Event.Target.ANY       ? allCurrentEvents : targetEventMap.get(params.target());
        Set<Event<?>> byType        = params.type() == Event.Type.ANY           ? allCurrentEvents : typeEventMap.get(params.type());
        Set<Event<?>> byCategory    = params.category() == Event.Category.ANY   ? allCurrentEvents : categoryEventMap.get(params.category());

        return intersection(List.of(byEmittorClass, byTargetClass, byTarget, byType, byCategory, byName));
    }

    /**
     * Adds an event for others to query for
     * @param e event
     */
    @Override
    public void publish(Event<?> e) {
        if(evaluateSubscribers(e)){
            return;
        }

        emitterClassEventMap.computeIfAbsent(e.getSourceType(), k -> new HashSet<>()).add(e);
        targetClassEventMap.computeIfAbsent(e.getTargetType(), k -> new HashSet<>()).add(e);
        nameEventMap.computeIfAbsent(e.getName(), k -> new HashSet<>()).add(e);

        typeEventMap.get(e.getType()).add(e);
        targetEventMap.get(e.getTarget()).add(e);
        categoryEventMap.get(e.getCategory()).add(e);

        allCurrentEvents.add(e);
    }


    @Override
    public void publishAll(Collection<Event<?>> events){
        events.forEach(this::publish);
    }

    @Override
    public void unpublishAll(Collection<Event<?>> events){
        events.forEach(this::unpublish);
    }

    /**
     * Removes the event
     * @param e event
     */
    @Override
    public void unpublish(Event<?> e) {
        emitterClassEventMap.computeIfAbsent(e.getSourceType(), k -> new HashSet<>()).remove(e);
        targetClassEventMap.computeIfAbsent(e.getTargetType(), k -> new HashSet<>()).remove(e);
        nameEventMap.computeIfAbsent(e.getName(), k -> new HashSet<>()).remove(e);

        typeEventMap.get(e.getType()).remove(e);
        targetEventMap.get(e.getTarget()).remove(e);
        categoryEventMap.get(e.getCategory()).remove(e);

        allCurrentEvents.remove(e);
    }

    private <T> Set<T> intersection(List<Set<T>> collections){
        if(collections.size() == 0) return Collections.emptySet();
        if(collections.size() == 1) return collections.get(0);

        Set<T> intersection  = new HashSet<>(collections.get(0));
        for(int i = 1; i < collections.size(); i++){
            intersection.retainAll(collections.get(i));
        }
        return intersection;
    }



}
