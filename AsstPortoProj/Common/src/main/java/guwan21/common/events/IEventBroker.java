package guwan21.common.events;

import java.util.Collection;

public interface IEventBroker {

    /**
     * If any parameter of the query is null, only null.
     * @param emittorClass The class of the object that presumably emitted the event
     * @param targetClass What class the event targets
     * @param target What is the target? A Service, Plugin, Entity...
     * @param type of event. Consumed or not.
     * @param category What category the event target belongs to: System, Gameplay, UI...
     * @return any events that matches all parameters.
     */
    Collection<Event<?>> querySpecific(Class<?> emittorClass, Class<?> targetClass, Event.Target target, Event.Type type, Event.Category category);

    /**
     * If any parameter of the query is null, any will match.
     * @param emittorClass The class of the object that presumably emitted the event
     * @param targetClass What class the event targets
     * @param target What is the target? A Service, Plugin, Entity...
     * @param type of event. Consumed or not.
     * @param category What category the event target belongs to: System, Gameplay, UI...
     * @return any events that matches all parameters.
     */
    Collection<Event<?>> queryAny(Class<?> emittorClass, Class<?> targetClass, Event.Target target, Event.Type type, Event.Category category);

    /**
     * Removes the event
     * @param e event
     */
    void removeEvent(Event<?> e);

    void addAll(Collection<Event<?>> events);
    void removeAll(Collection<Event<?>> events);

    /**
     * Adds an event for others to query for
     * @param e event
     */
    public void addEvent(Event<?> e);

}
