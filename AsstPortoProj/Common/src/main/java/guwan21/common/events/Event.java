package guwan21.common.events;

import guwan21.common.data.Entity;
import java.io.Serializable;

public class Event implements Serializable{
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}