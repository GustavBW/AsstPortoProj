package guwan21.event;

import guwan21.common.data.entities.Entity;
import guwan21.common.events.Event;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

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
        broker.addAll(events);

        //Testing source class distinction
        Collection<Event<?>> resultQ1 = broker.querySpecific(Entity.class, Object.class, Event.Target.SERVICE,  Event.Type.INSTANT, Event.Category.GAMEPLAY);

        assertEquals(1, resultQ1.size());
        assertEquals(event2, resultQ1.iterator().next());

        //Testing event type distinction
        Collection<Event<?>> resultQ2 = broker.querySpecific(Object.class, Object.class, Event.Target.SERVICE,  Event.Type.LINGERING, Event.Category.GAMEPLAY);

        assertEquals(1, resultQ2.size());
        assertEquals(event3, resultQ2.iterator().next());

        //Testing event category distinction
        Collection<Event<?>> resultQ3 = broker.querySpecific(Object.class, Object.class, Event.Target.SERVICE,  Event.Type.INSTANT, Event.Category.SYSTEM);

        assertEquals(1, resultQ3.size());
        assertEquals(event4, resultQ3.iterator().next());

        //Testing event target distinction
        Collection<Event<?>> resultQ4 = broker.querySpecific(Object.class, Object.class, Event.Target.ENTITY,  Event.Type.INSTANT, Event.Category.GAMEPLAY);

        assertEquals(1, resultQ4.size());
        assertEquals(event5, resultQ4.iterator().next());

        //Testing event target class distinction
        Collection<Event<?>> resultQ5 = broker.querySpecific(Object.class, Entity.class, Event.Target.SERVICE,  Event.Type.INSTANT, Event.Category.GAMEPLAY);

        assertEquals(1, resultQ5.size());
        assertEquals(event6, resultQ5.iterator().next());
    }

    @Test
    void executionTimeGetSpecific(){
        EventBroker broker = new EventBroker();
        broker.addAll(getEventsWhereOneAttributeHasChanged());
        long timeA = System.currentTimeMillis();
        for(int i = 0; i < 1_000_000; i++){
            broker.querySpecific(Object.class, Entity.class, Event.Target.ENTITY,  Event.Type.INSTANT, Event.Category.SYSTEM);
        }
        long deltaTime = System.currentTimeMillis() - timeA;
        System.out.println("Get specific, 1m runs: " + deltaTime + "ms");

    }
    @Test
    void executionTimeGetAny(){
        EventBroker broker = new EventBroker();
        broker.addAll(getEventsWhereMostShareAttributes());
        long timeA = System.currentTimeMillis();
        for(int i = 0; i < 1_000_000; i++){
            broker.queryAny(Object.class, Entity.class, Event.Target.ENTITY,  Event.Type.INSTANT, Event.Category.SYSTEM);
        }
        long deltaTime = System.currentTimeMillis() - timeA;
        System.out.println("Get specific, 1m runs: " + deltaTime + "ms");

    }

    @Test
    void getAny() {
        EventBroker broker = new EventBroker();
        broker.addAll(getEventsWhereMostShareAttributes());

        //There should be 8 events
        Collection<Event<?>> resultQ0 = broker.queryAny(null, null, null,  null, null);
        assertEquals(8,resultQ0.size());

        //There should be 8 events with source class "Object"
        Collection<Event<?>> resultQ1 = broker.queryAny(Object.class, null, null,  null, null);
        assertEquals(8,resultQ1.size());
        resultQ1.forEach(
                event -> assertEquals(event.getSource().getClass(), Object.class)
        );

        //There should be 5 events with event type "Instant"
        Collection<Event<?>> resultQ2 = broker.queryAny(null, null, null,  Event.Type.INSTANT, null);
        assertEquals(5,resultQ2.size());
        resultQ2.forEach(
                event -> assertEquals(event.getType(), Event.Type.INSTANT)
        );

        //There should be 5 events in category "Gameplay"
        Collection<Event<?>> resultQ3 = broker.queryAny(null, null, null,  null, Event.Category.GAMEPLAY);
        assertEquals(5,resultQ3.size());
        resultQ3.forEach(
                event -> assertEquals(event.getCategory(), Event.Category.GAMEPLAY)
        );

        //There should be 3 events with target "Service"
        Collection<Event<?>> resultQ4 = broker.queryAny(null, null, Event.Target.SERVICE,  null, null);
        assertEquals(3,resultQ4.size());
        resultQ4.forEach(
                event -> assertEquals(event.getTarget(), Event.Target.SERVICE)
        );

        //There should be 3 events with target class "Entity"
        Collection<Event<?>> resultQ5 = broker.queryAny(null, Entity.class, null,  null, null);
        assertEquals(3,resultQ5.size());
        resultQ5.forEach(
                event -> assertEquals(event.getTargetType(), Entity.class)
        );
    }

    @Test
    void addEvent() {
    }

    @Test
    void removeEvent() {
    }
}