package guwan21.asteroid;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IEntityProcessingService;

/**
 * Asteroid control system, that control the movement and behavior of all asteroids.
 */
public class AsteroidProcessingService implements IEntityProcessingService {

    private final AsteroidConstructor constructor = new AsteroidConstructor();

    @Override
    public void process(GameData data, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            asteroid.getParts().forEach(p -> p.process(data,asteroid));
            if (asteroid.getPart(LifePart.class).isDead()) {
                world.removeEntity(asteroid);
                continue;
            }
            constructor.setAsteroidRadius(asteroid);
            constructor.updateShape(asteroid);
        }
    }


}












