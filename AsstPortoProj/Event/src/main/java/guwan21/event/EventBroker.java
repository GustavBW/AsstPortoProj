package guwan21.event;

import guwan21.common.events.Event;
import guwan21.common.events.IEventBroker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventBroker implements IEventBroker {

    /**
     * Events by their emitter's class
     */
    private final Map<Class<?>, Set<Event<?>>> emitterClassEventMap = new ConcurrentHashMap<>();
    /**
     * Events by their target's class
     */
    private final Map<Class<?>, Set<Event<?>>> targetClassEventMap = new ConcurrentHashMap<>();

    /**
     * Events by their Event.Target enum value
     */
    private final Map<Event.Target, Set<Event<?>>> targetEventMap = new ConcurrentHashMap<>();
    /**
     * Events by their Event.Type enum value
     */
    private final Map<Event.Type, Set<Event<?>>> typeEventMap = new ConcurrentHashMap<>();
    /**
     * Events by their Event.Category enum value
     */
    private final Map<Event.Category, Set<Event<?>>> categoryEventMap = new ConcurrentHashMap<>();

    private final Set<Event<?>> allCurrent = new HashSet<>();

    public EventBroker(){
        //All enumerated can be prefilled
        Arrays.stream(Event.Target.values()).forEach(
                target -> targetEventMap.put(target, new HashSet<>())
        );
        Arrays.stream(Event.Type.values()).forEach(
                type -> typeEventMap.put(type, new HashSet<>())
        );
        Arrays.stream(Event.Category.values()).forEach(
                category -> categoryEventMap.put(category, new HashSet<>())
        );
    }

    /**
     * If any parameter of the query is null, only null.
     * @param emittorClass The class of the object that presumably emitted the event
     * @param targetClass What class the event targets
     * @param target What is the target? A Service, Plugin, Entity...
     * @param type of event. Consumed or not.
     * @param category What category the event target belongs to: System, Gameplay, UI...
     * @return any events that matches all parameters.
     */
    @Override
    public Collection<Event<?>> querySpecific(Class<?> emittorClass, Class<?> targetClass, Event.Target target, Event.Type type, Event.Category category) {
        Set<Event<?>> byEmittorClass= emitterClassEventMap.computeIfAbsent(emittorClass, k-> new HashSet<>());
        Set<Event<?>> byTargetClass = targetClassEventMap.computeIfAbsent(targetClass, k -> new HashSet<>());

        Set<Event<?>> byTarget      = targetEventMap.get(target);
        Set<Event<?>> byType        = typeEventMap.get(type);
        Set<Event<?>> byCategory    = categoryEventMap.get(category);

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
    }
    /**
     * If any parameter of the query is null, any will match.
     * @param emittorClass The class of the object that presumably emitted the event
     * @param targetClass What class the event targets
     * @param target What is the target? A Service, Plugin, Entity...
     * @param type of event. Consumed or not.
     * @param category What category the event target belongs to: System, Gameplay, UI...
     * @return any events that matches all parameters.
     */
    @Override
    public Collection<Event<?>> queryAny(Class<?> emittorClass, Class<?> targetClass, Event.Target target, Event.Type type, Event.Category category) {
        //gathering all values of each map is ineffective, as all values of any map is the same due to how its put in

        Set<Event<?>> byEmittorClass= emittorClass == null  ? allCurrent : emitterClassEventMap.computeIfAbsent(emittorClass, k -> new HashSet<>());
        Set<Event<?>> byTargetClass = targetClass == null   ? allCurrent : targetClassEventMap.computeIfAbsent(targetClass, k -> new HashSet<>());
        Set<Event<?>> byTarget      = target == null        ? allCurrent : targetEventMap.get(target);
        Set<Event<?>> byType        = type == null          ? allCurrent : typeEventMap.get(type);
        Set<Event<?>> byCategory    = category == null      ? allCurrent : categoryEventMap.get(category);

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
    }

    /**
     * Adds an event for others to query for
     * @param e event
     */
    @Override
    public void addEvent(Event<?> e) {
        emitterClassEventMap.computeIfAbsent(e.getSourceType(), k -> new HashSet<>()).add(e);
        targetClassEventMap.computeIfAbsent(e.getTargetType(), k -> new HashSet<>()).add(e);

        typeEventMap.get(e.getType()).add(e);
        targetEventMap.get(e.getTarget()).add(e);
        categoryEventMap.get(e.getCategory()).add(e);

        allCurrent.add(e);
    }


    @Override
    public void addAll(Collection<Event<?>> events){
        events.forEach(this::addEvent);
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

        allCurrent.remove(e);
    }

    private Set<Event<?>> intersection(Set<Event<?>> a, Set<Event<?>> b, Set<Event<?>> c, Set<Event<?>> d, Set<Event<?>> e){
        Set<Event<?>> intersection  = new HashSet<>(a);
        intersection.retainAll(b);
        intersection.retainAll(c);
        intersection.retainAll(d);
        intersection.retainAll(e);
        return intersection;
    }



}
