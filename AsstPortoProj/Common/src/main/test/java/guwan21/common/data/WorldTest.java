package guwan21.common.data;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorldTest {

    @Test
    void addEntity() {
    }

    @Test
    void removeEntity() {
    }

    @Test
    void testRemoveEntity() {
    }

    @Test
    void getEntities() {
        List<Entity> entityList = new LinkedList<>();
        entityList.add(mock(Entity.class));
        entityList.add(mock(Entity.class));
        when(mock(World.class).getEntities()).thenReturn(entityList);
    }

    @Test
    void removeEntities() {
    }

    @Test
    void testGetEntities() {
    }

    @Test
    void getEntity() {
    }
}