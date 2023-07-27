package guwan21.common.data.entityparts;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PositionPartTest {

    @Test
    void getX() {
        PositionPart part = new PositionPart(1,1,1);
        assertEquals(1, part.getX());
    }

    @Test
    void getY() {
        PositionPart part = new PositionPart(1,1,1);
        assertEquals(1, part.getY());
    }

    @Test
    void getRadians() {
        PositionPart part = new PositionPart(1,1,1);
        assertEquals(1, part.getRadians());
    }

    @Test
    void setX() {
        PositionPart part = new PositionPart(1,1,1);
        part.setX(.234869f);
        assertEquals(.234869f, part.getX());
    }

    @Test
    void setY() {
        PositionPart part = new PositionPart(1,1,1);
        part.setY(.234869f);
        assertEquals(.234869f, part.getY());
    }

    @Test
    void setPosition() {
        PositionPart part = new PositionPart(1,1,1);
        part.setPosition(.239487f,.12479f);
        assertEquals(.239487f, part.getX());
        assertEquals(.12479f,part.getY());
    }

    @Test
    void setRadians() {
        PositionPart part = new PositionPart(1,1,1);
        part.setRadians(3928629);
        assertEquals(3928629, part.getRadians());
    }

    @Test
    void process() {
        PositionPart part = new PositionPart(1,1,1);
        part.process(mock(GameData.class), mock(Entity.class));
        assertEquals(1,part.getY());
        assertEquals(1,part.getX());
        assertEquals(1,part.getRadians());
    }
}