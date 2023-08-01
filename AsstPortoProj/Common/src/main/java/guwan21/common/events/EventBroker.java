package guwan21.common.events;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventBroker {

    private final Map<Class<?>, Set<Event<?>>> emittorClassEventMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, Set<Event<?>>> targetClassEventMap = new ConcurrentHashMap<>();

    private final Map<Event.Target, Set<Event<?>>> targetEventMap = new ConcurrentHashMap<>();
    private final Map<Event.Type, Set<Event<?>>> typeEventMap = new ConcurrentHashMap<>();
    private final Map<Event.Category, Set<Event<?>>> categoryEventMap = new ConcurrentHashMap<>();

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
     * @param emittorClass of event
     * @param targetClass of event
     * @param target of event
     * @param type of event
     * @param category of event
     * @return any events that matches all parameters.
     */
    public Collection<Event<?>> getSpecific(Class<?> emittorClass, Class<?> targetClass, Event.Target target, Event.Type type, Event.Category category) {
        Set<Event<?>> byEmittorClass= emittorClassEventMap.get(emittorClass);
        Set<Event<?>> byTargetClass = targetClassEventMap.get(targetClass);
        Set<Event<?>> byTarget      = targetEventMap.get(target);
        Set<Event<?>> byType        = typeEventMap.get(type);
        Set<Event<?>> byCategory    = categoryEventMap.get(category);

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
    }
    /**
     * If any parameter of the query is null, any will match.
     * @param emittorClass of event
     * @param targetClass of event
     * @param target of event
     * @param type of event
     * @param category of event
     * @return any events that matches all parameters.
     */
    public Collection<Event<?>> getAny(Class<?> emittorClass, Class<?> targetClass, Event.Target target, Event.Type type, Event.Category category) {
        //gathering all values of each map is ineffective, as all values of any map is the same due to how its put in
        Set<Event<?>> allValues = flatten(emittorClassEventMap.values());

        Set<Event<?>> byEmittorClass= emittorClass == null  ? allValues : emittorClassEventMap.get(emittorClass);
        Set<Event<?>> byTargetClass = targetClass == null   ? allValues : targetClassEventMap.get(targetClass);
        Set<Event<?>> byTarget      = target == null        ? allValues : targetEventMap.get(target);
        Set<Event<?>> byType        = type == null          ? allValues : typeEventMap.get(type);
        Set<Event<?>> byCategory    = category == null      ? allValues : categoryEventMap.get(category);

        return intersection(byEmittorClass, byTargetClass, byTarget, byType, byCategory);
    }

    public void addEvent(Event<?> e) {
        emittorClassEventMap.computeIfAbsent(e.getSourceType(), k -> new HashSet<>()).add(e);
        targetClassEventMap.computeIfAbsent(e.getTargetType(), k -> new HashSet<>()).add(e);

        typeEventMap.get(e.getType()).add(e);
        targetEventMap.get(e.getTarget()).add(e);
        categoryEventMap.get(e.getCategory()).add(e);
    }

    public void removeEvent(Event<?> e) {
        emittorClassEventMap.computeIfAbsent(e.getSourceType(), k -> new HashSet<>()).remove(e);
        targetClassEventMap.computeIfAbsent(e.getTargetType(), k -> new HashSet<>()).remove(e);

        typeEventMap.get(e.getType()).remove(e);
        targetEventMap.get(e.getTarget()).remove(e);
        categoryEventMap.get(e.getCategory()).remove(e);
    }

    private <T> Set<T> flatten(Collection<Set<T>> nested){
        Set<T> collector = new HashSet<>();
        nested.forEach(collector::addAll);
        return collector;
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
