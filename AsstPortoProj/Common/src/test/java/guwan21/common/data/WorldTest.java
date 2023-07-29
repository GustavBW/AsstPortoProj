package guwan21.common.data;

import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorldTest {

    private final Entity entity = mock(Entity.class);

    @Test
    void testAddEntity() {
        World world = new World();
        String entityId = "testId";

        when(entity.getID()).thenReturn(entityId);

        String result = world.addEntity(entity);

        Collection<Entity> entitiesInWorld = world.getEntities();

        assertEquals(entityId, result);
        assertEquals(1,entitiesInWorld.size());
        assertEquals(entity, world.getEntity(entityId));

        verify(entity, times(1)).getID();
    }

    @Test
    void testRemoveEntity() {
        World world = new World();
        String entityId = "testId";

        when(entity.getID()).thenReturn(entityId);
        world.addEntity(entity); //Calls getID once
        world.removeEntity(entity); //Calls getID once more

        assertEquals(0, world.getEntities().size());
        assertNull(world.getEntity(entityId));

        verify(entity, times(2)).getID();
    }

    @Test
    void testGetEntities() {
        World world = new World();
        String entityId = "testId";

        when(entity.getID()).thenReturn(entityId);
        world.addEntity(entity);
        Collection<Entity> entities = world.getEntities();
        assertEquals(1, entities.size());
        assertEquals(entity, entities.iterator().next());
        verify(entity, times(1)).getID();
    }

    @Test
    void testRemoveEntities() {
        World world = new World();

        String entityId1 = "testId1";
        String entityId2 = "testId2";
        String entityId3 = "testId3";

        Entity entity1 = mock(Entity.class);
        Entity entity2 = mock(Entity.class);
        Entity entity3 = mock(Entity.class);

        when(entity1.getID()).thenReturn(entityId1);
        when(entity2.getID()).thenReturn(entityId2);
        when(entity3.getID()).thenReturn(entityId3);

        world.addEntity(entity1);
        world.addEntity(entity2);
        world.addEntity(entity3);

        // Assuming you want to remove all entities of a given type, let's say Entity.class
        world.removeEntities(Entity.class);

        assertEquals(0, world.getEntities().size());
        assertNull(world.getEntity(entityId1));
        assertNull(world.getEntity(entityId2));
        assertNull(world.getEntity(entityId3));
    }

    @Test
    void testGetEntitiesByClass() {
        World world = new World();

        String entityId1 = "testId1";
        String entityId2 = "testId2";

        Entity entity1 = mock(Entity.class);
        Entity entity2 = mock(Entity.class);

        when(entity1.getID()).thenReturn(entityId1);
        when(entity2.getID()).thenReturn(entityId2);

        world.addEntity(entity1);
        world.addEntity(entity2);

        List<Entity> entities = world.getEntities(Entity.class);

        assertEquals(2, entities.size());
        assertEquals(entity1, entities.get(0));
        assertEquals(entity2, entities.get(1));
    }

    @Test
    void testGetEntitiesByClassName() {
        //Mockito cant mock getClass, so going JUnit for this one
        World world = new World();
        Asteroid asteroid = new Asteroid();
        String asteroidId = asteroid.getID();
        Bullet bullet = new Bullet();
        String bulletId = bullet.getID();

        world.addEntities(asteroid,bullet);

        //When getting by the base class - Entity
        List<Entity> entities = world.getEntities("Entity");
        //An empty list should be returned
        assertNotNull(entities);
        assertEquals(0,entities.size());

        //When getting by the class name "Asteroid"
        List<Entity> asteroids = world.getEntities("Asteroid");
        //A list of one item should be returned
        assertNotNull(asteroids);
        assertEquals(1,asteroids.size());
        //And the id of that item should be asteroidsId
        assertEquals(asteroidId, asteroids.get(0).getID());

        //And likewise for Bullet

        //When getting by the class name "Bullet"
        List<Entity> bullets = world.getEntities("Bullet");
        //A list of one item should be returned
        assertNotNull(bullets);
        assertEquals(1,bullets.size());
        //And the id of that item should be asteroidsId
        assertEquals(bulletId, bullets.get(0).getID());
    }
}