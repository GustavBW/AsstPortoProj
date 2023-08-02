package guwan21.common.events;

import java.util.Collection;
import java.util.function.Function;

public interface IEventBroker {

    /**
     * If any parameter of the query is null, only null.
     * @return any events that matches all parameters.
     */
    Collection<Event<?>> querySpecific(EventQueryParameters params);

    /**
     * If any parameter of the query is null, any will match.
     * @return any events that matches all parameters.
     */
    Collection<Event<?>> queryAny(EventQueryParameters params);

    /**
     * Subscribes to events matching specific attributes. The subscription is only active for any new events. Acts like queryAny: If a parameter of the query is null, any will match.
     * @param callback - function to run. If it returns true, and the event is of type Instant, the event is consumed when all subscribed callbacks have been run.
     */
    Function<Event<?>,Boolean> subscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params);

    /**
     * Tries to access where that callback is stored based on the params, then removes it.
     * @param callback
     */
    void unsubscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params);

    /**
     * Removes the event
     * @param e event
     */
    void removeEvent(Event<?> e);

    void publishAll(Collection<Event<?>> events);
    void removeAll(Collection<Event<?>> events);

    /**
     * Adds an event for others to query for
     * @param e event
     */
    public void publish(Event<?> e);

}
