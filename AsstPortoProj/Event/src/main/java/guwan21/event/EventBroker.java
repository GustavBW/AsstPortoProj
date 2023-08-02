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

    private final Set<Event<?>> allCurrentEvents = new HashSet<>();
    private final Set<Function<Event<?>,Boolean>> allCurrentSubscribers = new HashSet<>();

    public EventBroker(){
        //All enumerated can be prefilled
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
    }

    @Override
    public Function<Event<?>,Boolean> subscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params){
        emitterClassSubscriberMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>()).add(callback);
        targetClassSubscriberMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>()).add(callback);

        targetSubscriberMap.get(params.target()).add(callback);
        typeSubscriberMap.get(params.type()).add(callback);
        categorySubscriberMap.get(params.category()).add(callback);

        allCurrentSubscribers.add(callback);

        return callback;
    }

    @Override
    public void unsubscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params){
        emitterClassSubscriberMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>()).remove(callback);
        targetClassSubscriberMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>()).remove(callback);

        typeSubscriberMap.get(params.type()).remove(callback);
        targetSubscriberMap.get(params.target()).remove(callback);
        categorySubscriberMap.get(params.category()).remove(callback);

        allCurrentSubscribers.remove(callback);
    }

    private Collection<Function<Event<?>,Boolean>> querySubscribers(EventQueryParameters params){
        Set<Function<Event<?>,Boolean>> byEmittorClass= params.emittorClass() == null  ? allCurrentSubscribers : emitterClassSubscriberMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>());
        Set<Function<Event<?>,Boolean>> byTargetClass = params.targetClass() == null   ? allCurrentSubscribers : targetClassSubscriberMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>());
        Set<Function<Event<?>,Boolean>> byTarget      = params.target() == null        ? allCurrentSubscribers : targetSubscriberMap.get(params.target());
        Set<Function<Event<?>,Boolean>> byType        = params.type() == null          ? allCurrentSubscribers : typeSubscriberMap.get(params.type());
        Set<Function<Event<?>,Boolean>> byCategory    = params.category() == null      ? allCurrentSubscribers : categorySubscriberMap.get(params.category());

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
    }

    /**
     * Queries subscribers, returns true if any subscriber tried to consume the event.
     * @param event event to query subscribers for
     * @return true if consumed
     */
    private boolean evaluateSubscribers(Event<?> event){
        EventQueryParameters params = new EventQueryParameters(event.getSourceType(),event.getTargetType(),event.getTarget(),event.getType(),event.getCategory());
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

        Set<Event<?>> byTarget      = targetEventMap.get(params.target());
        Set<Event<?>> byType        = typeEventMap.get(params.type());
        Set<Event<?>> byCategory    = categoryEventMap.get(params.category());

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
    }
    /**
     * If any parameter of the query is null, any will match.
     * @param params Query Parameters
     * @return any events that matches all parameters.
     */
    @Override
    public Collection<Event<?>> queryAny(EventQueryParameters params) {
        //gathering all values of each map is ineffective, as all values of any map is the same due to how its put in

        Set<Event<?>> byEmittorClass= params.emittorClass() == null  ? allCurrentEvents : emitterClassEventMap.computeIfAbsent(params.emittorClass(), k -> new HashSet<>());
        Set<Event<?>> byTargetClass = params.targetClass() == null   ? allCurrentEvents : targetClassEventMap.computeIfAbsent(params.targetClass(), k -> new HashSet<>());
        Set<Event<?>> byTarget      = params.target() == null        ? allCurrentEvents : targetEventMap.get(params.target());
        Set<Event<?>> byType        = params.type() == null          ? allCurrentEvents : typeEventMap.get(params.type());
        Set<Event<?>> byCategory    = params.category() == null      ? allCurrentEvents : categoryEventMap.get(params.category());

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
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
    public void removeAll(Collection<Event<?>> events){
        events.forEach(this::removeEvent);
    }

    /**
     * Removes the event
     * @param e event
     */
    @Override
    public void removeEvent(Event<?> e) {
        emitterClassEventMap.computeIfAbsent(e.getSourceType(), k -> new HashSet<>()).remove(e);
        targetClassEventMap.computeIfAbsent(e.getTargetType(), k -> new HashSet<>()).remove(e);

        typeEventMap.get(e.getType()).remove(e);
        targetEventMap.get(e.getTarget()).remove(e);
        categoryEventMap.get(e.getCategory()).remove(e);

        allCurrentEvents.remove(e);
    }

    private <T> Set<T> intersection(Set<T> a, Set<T> b, Set<T> c, Set<T> d, Set<T> e){
        Set<T> intersection  = new HashSet<>(a);
        intersection.retainAll(b);
        intersection.retainAll(c);
        intersection.retainAll(d);
        intersection.retainAll(e);
        return intersection;
    }



}
