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
        World mockWorld = mock(World.class);
        GameData mockData = mock(GameData.class);
        plugin.start(mockData, mockWorld);

        verify(mockWorld, times(1)).addEntity(any(Player.class));
        verify(mockWorld, never()).removeEntity(any(Player.class));
    }

    @Test
    void stop() {
        IGamePluginService plugin = new PlayerPlugin();
        World mockWorld = mock(World.class);
        GameData mockData = mock(GameData.class);

        plugin.stop(mockData, mockWorld);

        verify(mockWorld, times(1)).removeEntities(Player.class);
        verify(mockWorld, never()).addEntity(any(Player.class));
    }
}