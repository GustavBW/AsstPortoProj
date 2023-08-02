package guwan21.event;

import guwan21.common.data.entities.Entity;
import guwan21.common.events.Event;
import guwan21.common.events.EventQueryParameters;
import guwan21.common.events.IEventBroker;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class EventBrokerTest {

    /**
     * @return none, source class, type, category, target, target class
     */
    private List<Event<?>> getEventsWhereOneAttributeHasChanged(){
        return List.of(
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Entity(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.LINGERING, Event.Category.GAMEPLAY,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.SYSTEM,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.ENTITY)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.SERVICE)
                        .setTargetType(Entity.class)
        );
    }

    /**
     * 8 events: <br>
     *  - source class: Object: 8 <br>
     *  - event type: Instant 5, Lingering 3 <br>
     *  - event category: Gameplay 5, System 3 <br>
     *  - event target: Service 3, Entity 5 <br>
     *  - target class: Object 5, Entity 3 <br>
     */
    private List<Event<?>> getEventsWhereMostShareAttributes(){
        return List.of(
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.LINGERING, Event.Category.GAMEPLAY,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.LINGERING, Event.Category.SYSTEM,Event.Target.SERVICE)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.LINGERING, Event.Category.SYSTEM,Event.Target.ENTITY)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.SYSTEM,Event.Target.ENTITY)
                        .setTargetType(Object.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.ENTITY)
                        .setTargetType(Entity.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.ENTITY)
                        .setTargetType(Entity.class),
                new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.ENTITY)
                        .setTargetType(Entity.class)
        );
    }

    @Test
    void getSpecific() {
        EventBroker broker = new EventBroker();
        List<Event<?>> events = getEventsWhereOneAttributeHasChanged();
        Event<?> event1 = events.get(0); //baseline
        Event<?> event2 = events.get(1); //source class changed
        Event<?> event3 = events.get(2); //event type changed
        Event<?> event4 = events.get(3); //event category changed
        Event<?> event5 = events.get(4); //event target changed
        Event<?> event6 = events.get(5); //event target class changed
        broker.publishAll(events);

        //Testing source class distinction
        Collection<Event<?>> resultQ1 = broker.querySpecific(new EventQueryParameters(Entity.class, Object.class, Event.Target.SERVICE,  Event.Type.INSTANT, Event.Category.GAMEPLAY));

        assertEquals(1, resultQ1.size());
        assertEquals(event2, resultQ1.iterator().next());

        //Testing event type distinction
        Collection<Event<?>> resultQ2 = broker.querySpecific(new EventQueryParameters(Object.class, Object.class, Event.Target.SERVICE,  Event.Type.LINGERING, Event.Category.GAMEPLAY));

        assertEquals(1, resultQ2.size());
        assertEquals(event3, resultQ2.iterator().next());

        //Testing event category distinction
        Collection<Event<?>> resultQ3 = broker.querySpecific(new EventQueryParameters(Object.class, Object.class, Event.Target.SERVICE,  Event.Type.INSTANT, Event.Category.SYSTEM));

        assertEquals(1, resultQ3.size());
        assertEquals(event4, resultQ3.iterator().next());

        //Testing event target distinction
        Collection<Event<?>> resultQ4 = broker.querySpecific(new EventQueryParameters(Object.class, Object.class, Event.Target.ENTITY,  Event.Type.INSTANT, Event.Category.GAMEPLAY));

        assertEquals(1, resultQ4.size());
        assertEquals(event5, resultQ4.iterator().next());

        //Testing event target class distinction
        Collection<Event<?>> resultQ5 = broker.querySpecific(new EventQueryParameters(Object.class, Entity.class, Event.Target.SERVICE,  Event.Type.INSTANT, Event.Category.GAMEPLAY));

        assertEquals(1, resultQ5.size());
        assertEquals(event6, resultQ5.iterator().next());
    }

    @Test
    void executionTimeGetSpecific(){
        EventBroker broker = new EventBroker();
        broker.publishAll(getEventsWhereOneAttributeHasChanged());
        long timeA = System.currentTimeMillis();
        EventQueryParameters params = new EventQueryParameters(Object.class, Entity.class, Event.Target.ENTITY,  Event.Type.INSTANT, Event.Category.SYSTEM);
        for(int i = 0; i < 1_000_000; i++){
            broker.querySpecific(params);
        }
        long deltaTime = System.currentTimeMillis() - timeA;
        System.out.println("Get specific, 1m runs: " + deltaTime + "ms");

    }
    @Test
    void executionTimeGetAny(){
        EventBroker broker = new EventBroker();
        broker.publishAll(getEventsWhereMostShareAttributes());
        long timeA = System.currentTimeMillis();
        EventQueryParameters params = new EventQueryParameters(Object.class, Entity.class, Event.Target.ENTITY,  Event.Type.INSTANT, Event.Category.SYSTEM);
        for(int i = 0; i < 1_000_000; i++){
            broker.queryAny(params);
        }
        long deltaTime = System.currentTimeMillis() - timeA;
        System.out.println("Get specific, 1m runs: " + deltaTime + "ms");

    }

    @Test
    void getAny() {
        EventBroker broker = new EventBroker();
        broker.publishAll(getEventsWhereMostShareAttributes());

        //There should be 8 events
        Collection<Event<?>> resultQ0 = broker.queryAny(new EventQueryParameters(null, null, null,  null, null));
        assertEquals(8,resultQ0.size());

        //There should be 8 events with source class "Object"
        Collection<Event<?>> resultQ1 = broker.queryAny(new EventQueryParameters(Object.class, null, null,  null, null));
        assertEquals(8,resultQ1.size());
        resultQ1.forEach(
                event -> assertEquals(event.getSource().getClass(), Object.class)
        );

        //There should be 5 events with event type "Instant"
        Collection<Event<?>> resultQ2 = broker.queryAny(new EventQueryParameters(null, null, null,  Event.Type.INSTANT, null));
        assertEquals(5,resultQ2.size());
        resultQ2.forEach(
                event -> assertEquals(event.getType(), Event.Type.INSTANT)
        );

        //There should be 5 events in category "Gameplay"
        Collection<Event<?>> resultQ3 = broker.queryAny(new EventQueryParameters(null, null, null,  null, Event.Category.GAMEPLAY));
        assertEquals(5,resultQ3.size());
        resultQ3.forEach(
                event -> assertEquals(event.getCategory(), Event.Category.GAMEPLAY)
        );

        //There should be 3 events with target "Service"
        Collection<Event<?>> resultQ4 = broker.queryAny(new EventQueryParameters(null, null, Event.Target.SERVICE,  null, null));
        assertEquals(3,resultQ4.size());
        resultQ4.forEach(
                event -> assertEquals(event.getTarget(), Event.Target.SERVICE)
        );

        //There should be 3 events with target class "Entity"
        Collection<Event<?>> resultQ5 = broker.queryAny(new EventQueryParameters(null, Entity.class, null,  null, null));
        assertEquals(3,resultQ5.size());
        resultQ5.forEach(
                event -> assertEquals(event.getTargetType(), Entity.class)
        );
    }

    @Test
    void subscribeAndUnsubscribe(){
        IEventBroker broker = new EventBroker();
        AtomicInteger invocationCounter = new AtomicInteger(); //Could be done with Mockito
        AtomicReference<Event<?>> eventInvokedWith = new AtomicReference<>();
        Function<Event<?>,Boolean> subscriber1 = event -> {
            invocationCounter.getAndIncrement();
            eventInvokedWith.set(event);
            return true;
        };
        Function<Event<?>,Boolean> subscriber2 = event -> {
            invocationCounter.getAndIncrement();
            eventInvokedWith.set(event);
            return true;
        };
        EventQueryParameters params2 = new EventQueryParameters(Object.class, Object.class, Event.Target.ENTITY, Event.Type.INSTANT, Event.Category.GAMEPLAY);
        EventQueryParameters params1 = new EventQueryParameters(Object.class, Object.class, Event.Target.SERVICE, Event.Type.INSTANT, Event.Category.GAMEPLAY);
        broker.subscribe(subscriber1,params1);
        broker.subscribe(subscriber2,params2);

        //When subscribed and an event is published:
        Event<?> eventPublished = new Event<>(new Object(), params1.type(), params1.category(), params1.target()).setTargetType(Object.class);
        broker.publish(eventPublished);

        //We expect one subscriber to be invoked once, with that specific event - since it matches the params
        assertEquals(1, invocationCounter.get());
        assertEquals(eventPublished, eventInvokedWith.get());

        //However, if we then unsubscribe
        broker.unsubscribe(subscriber1, params1);
        //But issue the event again:
        broker.publish(eventPublished);

        //There should be no additional invocations
        assertEquals(1, invocationCounter.get());


        //If we throw the old subscriber and a new subscriber into the mix
        EventQueryParameters params3 = new EventQueryParameters(null, Object.class, Event.Target.ENTITY, Event.Type.INSTANT, null);
        Function<Event<?>,Boolean> subscriber3 = event -> {
            invocationCounter.getAndIncrement();
            eventInvokedWith.set(event);
            return true;
        };
        broker.subscribe(subscriber3,params3);
        broker.subscribe(subscriber1,params1);
        //Alongside an event that matches none of them, but should match the new one because it is less specific
        Event<?> secondEventPublished = new Event<>(new Entity(), params3.type(), Event.Category.SYSTEM, params3.target()).setTargetType(Object.class);
        //reset the counters:
        invocationCounter.set(0);
        eventInvokedWith.set(null);

        broker.publish(secondEventPublished);
        //We should expect only this new subscriber to have incremented the values
        assertEquals(1, invocationCounter.get());
        assertEquals(eventPublished, eventInvokedWith.get());
    }

    @Test
    void addEvent() {

    }

    @Test
    void removeEvent() {
        IEventBroker broker = new EventBroker();
        Event<?> event = new Event<>(new Object(), Event.Type.INSTANT, Event.Category.GAMEPLAY,Event.Target.ENTITY)
                .setTargetType(Entity.class);
        broker.publish(event);

        Collection<Event<?>> resultQ1 = broker.querySpecific(new EventQueryParameters(Object.class, Entity.class, Event.Target.ENTITY, Event.Type.INSTANT, Event.Category.GAMEPLAY));

        assertEquals(1, resultQ1.size());
        assertEquals(event,resultQ1.iterator().next());

        broker.unpublish(event);

        Collection<Event<?>> resultQ2 = broker.querySpecific(new EventQueryParameters(Object.class, Entity.class, Event.Target.ENTITY, Event.Type.INSTANT, Event.Category.GAMEPLAY));

        assertEquals(0, resultQ2.size());
        assertThrows(NoSuchElementException.class,() -> resultQ2.iterator().next());
    }
}