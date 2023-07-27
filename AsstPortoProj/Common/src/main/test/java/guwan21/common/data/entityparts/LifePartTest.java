package guwan21.common.data.entityparts;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LifePartTest {

    @org.junit.jupiter.api.Test
    void getLife() {
        LifePart part = new LifePart(1,0);

        assertEquals(1, part.getLife());
    }

    @org.junit.jupiter.api.Test
    void setLife() {
        LifePart part = mock(LifePart.class);
        part.setLife(0);

        verify(part,times(1)).setLife(0);
    }

    @org.junit.jupiter.api.Test
    void isIsHit() {
        LifePart part = mock(LifePart.class);
        part.setIsHit(true);
        assertTrue(part.isHit());
    }

    @org.junit.jupiter.api.Test
    void setIsHit() {
        LifePart part = mock(LifePart.class);
        part.setIsHit(true);
        assertTrue(part.isHit());
    }

    @org.junit.jupiter.api.Test
    void getExpiration() {

    }

    @org.junit.jupiter.api.Test
    void isDead() {
        LifePart part = new LifePart(1,1000);
        part.setIsHit(true);
        part.process(mock(GameData.class), mock(Entity.class));
        assertTrue(part.isDead());
    }

    @org.junit.jupiter.api.Test
    void setExpiration() {
        LifePart part = new LifePart(1,1);
        GameData data = new GameData();
        data.setDelta(1);

        part.setExpiration(1);
        assertEquals(1, part.getExpiration());

        part.process(data,mock(Entity.class));
        assertEquals(0,part.getExpiration());
    }

    @org.junit.jupiter.api.Test
    void reduceExpiration() {
        LifePart part = new LifePart(1,1);
        part.reduceExpiration(1);
        assertEquals(0,part.getExpiration());
    }

    @org.junit.jupiter.api.Test
    void process() {
        LifePart part = new LifePart(1,1000);
        GameData data = mock(GameData.class);
        Entity entity = mock(Entity.class);
        part.setIsHit(true);    //On process, if hit, reduce hp by 1
        part.process(data,entity);

        assertEquals(0,part.getLife());
        assertFalse(part.isDead()); //However, not dead yet
        assertFalse(part.isHit()); //The hit is evaluated, so no longer hit.

        part.process(data,entity); //It takes 2 ticks for a lifepart to be "dead".
        assertTrue(part.isDead());
        assertFalse(part.isHit());
        assertEquals(0,part.getLife());
    }
}