package guwan21.common.events;

import guwan21.common.data.entities.Entity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;

public class EventBroker {

    private final Map<CompositeHashKey, Event<?>> specificMap = new ConcurrentHashMap<>();
    private final ConcurrentNavigableMap<CompositeHashKey, Event<?>> queryMap = new ConcurrentSkipListMap<>();
    private final ConcurrentNavigableMap<CompositeHashKey, Function<Event<?>,Boolean>> subscribers = new ConcurrentSkipListMap<>();

    public void addEvent(Event<?> e) {
        CompositeHashKey key = e.getCompositeKey();

        boolean consumed = false;
        for(Function<Event<?>,Boolean> subscriber : subscribers.subMap(key, true, key, true).values()){
            if(subscriber.apply(e)){
                consumed = true;
            }
        }

        if(e.getType() == Event.Type.INSTANT && consumed){
            return;
        }

        specificMap.put(e.getCompositeKey(),e);
        queryMap.put(e.getCompositeKey(), e);
    }

    public void removeEvent(Event<?> e) {
        CompositeHashKey key = e.getCompositeKey();
        specificMap.remove(key);
        queryMap.remove(key);
    }

    public Event<?> getEvent(CompositeHashKey compositeKey) {
        return specificMap.get(compositeKey);
    }

    public Collection<Event<?>> getEvents(CompositeHashKey compositeKey) {
        return queryMap.subMap(compositeKey, true, compositeKey, true).values();
    }

}
