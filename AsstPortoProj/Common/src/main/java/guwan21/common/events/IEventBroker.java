package guwan21.common.events;

import guwan21.common.services.SPI;

import java.util.Collection;
import java.util.function.Function;

public interface IEventBroker extends SPI {

    /**
     * If any parameter of the query is null, only null.<br>
     * @return any events that matches all parameters.
     */
    Collection<Event<?>> querySpecific(EventQueryParameters params);

    /**
     * If any parameter of the query is null, any will match.<br>
     * @return any events that matches all parameters.
     */
    Collection<Event<?>> queryAny(EventQueryParameters params);

    /**
     * Subscribes to events matching specific attributes. The subscription is only active for any new events. Acts like queryAny: If a parameter of the query is null, any will match.<br>
     * @param callback - function to run. If it returns true, and the event is of type Instant, the event is consumed when all subscribed callbacks have been run.
     */
    Function<Event<?>,Boolean> subscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params);

    /**
     * Tries to access where that callback is stored based on the params, then removes it.<br>
     * @param callback
     */
    void unsubscribe(Function<Event<?>,Boolean> callback, EventQueryParameters params);

    /**
     * Removes the event<br>
     * @param e event
     */
    void unpublish(Event<?> e);

    void publishAll(Collection<Event<?>> events);
    void unpublishAll(Collection<Event<?>> events);

    /**
     * Adds an event for others to query for<br>
     * @param e event
     */
    public void publish(Event<?> e);

}
