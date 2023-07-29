package guwan21.player;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.services.IGamePluginService;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerPluginTest {

    @BeforeAll
    static void setUp() {
    }

    @Test
    void start() {
        IGamePluginService plugin = new PlayerPlugin();
        World world = mock(World.class);
        plugin.start(mock(GameData.class), world);
        verify(world, times(1)).addEntity(any(Player.class));
        verify(world, never()).removeEntity(any(Player.class));
    }

    @Test
    void stop() {
        IGamePluginService plugin = new PlayerPlugin();
        World world = mock(World.class);
        plugin.stop(mock(GameData.class), world);
        verify(world, times(1)).removeEntities(Player.class);
        verify(world, never()).addEntity(any(Player.class));
    }
}