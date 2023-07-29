package guwan21.common.data;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void add() {
        Entity e = new Entity();
        e.add(new PositionPart(293847,5472,14260));
        PositionPart retrievedPart = e.getPart(PositionPart.class);
        assertNotNull(retrievedPart);
        assertEquals(293847, retrievedPart.getX());
        assertEquals(5472, retrievedPart.getY());
        assertEquals(14260,retrievedPart.getRadians());
    }

    @Test
    void remove() {
        Entity e = new Entity();
        e.add(new PositionPart(293847,5472,14260));
        e.add(new LifePart(293847,14260));
        assertNotNull(e.getPart(PositionPart.class));

        e.remove(PositionPart.class);
        assertNull(e.getPart(PositionPart.class));
        assertNotNull(e.getPart(LifePart.class)); //Only remove the one specified
    }

    @Test
    void getPart() {
        Entity e = new Entity();
        PositionPart posPart = new PositionPart(293847,5472,14260);
        e.add(posPart);
        LifePart lifePart = new LifePart(293847,14260);
        e.add(lifePart);
        assertEquals(posPart, e.getPart(PositionPart.class));
        assertEquals(lifePart, e.getPart(LifePart.class));

        PositionPart posPart2 = new PositionPart(1,1,1);
        e.add(posPart2);
        assertNotEquals(posPart, e.getPart(PositionPart.class)); //Object equivalence shall fail on different mem refs.
    }

    @Test
    void getParts() {
        Entity e = new Entity();
        PositionPart posPart = new PositionPart(293847,5472,14260);
        e.add(posPart);
        LifePart lifePart = new LifePart(293847,14260);
        e.add(lifePart);
        e.add(new LifePart(1,1)); //Size of value set of Map should not increase when the value entry for a key is overwritten

        assertEquals(2, e.getParts().size());

        e.remove(LifePart.class);
        assertEquals(1, e.getParts().size());
    }

    @Test
    void setRadius() {
        Entity e = new Entity();
        e.setRadius(1239571);
        assertEquals(1239571,e.getRadius());
    }

    @Test
    void getRadius() {
        Entity e = new Entity();
        e.setRadius(1239571);
        assertEquals(1239571,e.getRadius());
    }

    @Test
    void getID() {
        final List<Entity> list = new ArrayList<>(1000);
        for(int i = 0; i < 1000; i++){
            list.add(new Entity());
        }
        final Set<String> idSet = new HashSet<>();
        list.forEach(entity -> idSet.add(entity.getID()));
        assertEquals(list.size(),idSet.size()); //Expect 1000 unique ids
    }

}