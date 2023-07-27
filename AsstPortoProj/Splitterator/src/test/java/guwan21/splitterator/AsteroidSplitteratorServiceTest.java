package guwan21.splitterator;

import guwan21.asteroid.Asteroid;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsteroidSplitteratorServiceTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.Test
    void process() {
        //If an asteroid exists in the world
        World world = mock(World.class);
        Asteroid asteroid = mock(Asteroid.class);
        when(world.getEntities(Asteroid.class)).thenReturn(List.of(asteroid));

        //And its lifepart has noticed it has been hit by... something
        LifePart lp = mock(LifePart.class);
        when(lp.isHit()).thenReturn(true);
        when(asteroid.getPart(LifePart.class)).thenReturn(lp);

        //Then the Splitterator service should call the FragmentationPlugin's fracture method
        AsteroidFragmentationPlugin fragmentationPlugin = mock(AsteroidFragmentationPlugin.class); // Mock the FragmentationPlugin
        when(fragmentationPlugin.fracture(eq(world), eq(asteroid))).thenReturn(new Asteroid());

        //When its process method is called
        AsteroidSplitteratorService service = new AsteroidSplitteratorService(fragmentationPlugin);
        service.process(mock(GameData.class), world);

        // And add the fragment to the world
        verify(world, times(1)).addEntity(any(Asteroid.class));
    }
}