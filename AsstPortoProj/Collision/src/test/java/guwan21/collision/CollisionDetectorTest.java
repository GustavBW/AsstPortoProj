package guwan21.collision;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CollisionDetectorTest {
    private CollisionDetector collisionDetector;

    @BeforeEach
    void setUp() {
        collisionDetector = new CollisionDetector();
    }

    private LifePart initWorldState(
            World world,
            int entity1Life,
            float entity1X,
            float entity1Y,
            float entity1R,
            String e1Id,
            int entity2Life,
            float entity2X,
            float entity2Y,
            float entity2R,
            String e2Id
    ) {
        //Establishing mock e1
        Entity entity1 = mock(Entity.class);
        LifePart e1LifePart = mock(LifePart.class);
        PositionPart e1PosPart = mock(PositionPart.class);
        //Defining mock behaviour
        when(e1LifePart.getLife()).thenReturn(entity1Life);
        when(e1LifePart.getExpiration()).thenReturn(1_000_000f);

        when(e1PosPart.getX()).thenReturn(entity1X);
        when(e1PosPart.getY()).thenReturn(entity1Y);

        when(entity1.getRadius()).thenReturn(entity1R);
        when(entity1.getPart(PositionPart.class)).thenReturn(e1PosPart);
        when(entity1.getPart(LifePart.class)).thenReturn(e1LifePart);
        when(entity1.getID()).thenReturn(e1Id);

        //Establishing mock e1
        Entity entity2 = mock(Entity.class);
        PositionPart e2PosPart = mock(PositionPart.class);
        LifePart e2LifePart = mock(LifePart.class);
        //Defining mock behaviour
        when(e2PosPart.getX()).thenReturn(entity2X);
        when(e2PosPart.getY()).thenReturn(entity2Y);

        when(e2LifePart.getLife()).thenReturn(entity2Life);
        when(e2LifePart.getExpiration()).thenReturn(1_000_000f);

        when(entity2.getRadius()).thenReturn(entity2R);
        when(entity2.getPart(PositionPart.class)).thenReturn(e2PosPart);
        when(entity2.getPart(LifePart.class)).thenReturn(e2LifePart);
        when(entity2.getID()).thenReturn(e2Id);

        //"Adding" the "entities" to the "world"
        when(world.getEntities()).thenReturn(List.of(entity1,entity2));

        return e1LifePart;
    }

    @Test
    void shouldntHitOnPosition() {
        GameData data = mock(GameData.class);
        World world = mock(World.class);

        LifePart lifepart = initWorldState(
                world,
                1,
                0,
                0,
                1,
                "1",
                1,
                2,
                1,
                1,
                "2"
        );

        collisionDetector.process(data, world);

        verify(lifepart, never()).setIsHit(anyBoolean());
    }



    @Test
    void shouldHitOnPosition() {
        World world = mock(World.class);

        LifePart lifepart = initWorldState(
                world,
                1,
                0,
                0,
                2,
                "1",
                1,
                0,
                0,
                2,
                "2"
        );

        collisionDetector.process(mock(GameData.class), world);

        verify(lifepart).setIsHit(true);
    }

    @Test
    void shouldntHitOnId(){
        World world = mock(World.class);
        LifePart lpe1 = initWorldState(
                world,
                0,
                0,
                0,
                1,
                "1",
                0,
                0,
                0,
                1,
                "1"
        );
        collisionDetector.process(mock(GameData.class),world);
        verify(lpe1,never()).setIsHit(anyBoolean());
    }

    @Test
    void collides() {
        assertTrue(collisionDetector.withinBounds(0, 0, 6, 10, 0, 6));
        assertTrue(collisionDetector.withinBounds(0, 0, 5.000001f, 10, 0, 5.000001f));
        assertTrue(collisionDetector.withinBounds(0, 0, 10, 10, 10, 10));

        assertFalse(collisionDetector.withinBounds(0, 0, 5, 10, 0, 4));
        assertFalse(collisionDetector.withinBounds(0, 0, 5, 10, 10, 5));
    }
}