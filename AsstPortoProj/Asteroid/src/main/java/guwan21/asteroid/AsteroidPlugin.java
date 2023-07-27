package guwan21.asteroid;

import java.lang.Math;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {

    private final AsteroidConstructor constructor = new AsteroidConstructor();

    public AsteroidPlugin() {
    }

    @Override
    public void start(GameData data, World world) {
        for (int i = 0; i < Math.floor(Math.random() * 15) + 5; i++) {
            Entity asteroid = constructor.create(data.getDisplayWidth(), data.getDisplayHeight());
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData data, World world) {
        world.removeEntities(Asteroid.class);
    }
}
