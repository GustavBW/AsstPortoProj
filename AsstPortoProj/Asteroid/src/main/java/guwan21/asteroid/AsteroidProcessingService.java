package guwan21.asteroid;

import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.EntityConstructionServiceRegistry;

/**
 * Asteroid control system, that control the movement and behavior of all asteroids.
 */
public class AsteroidProcessingService implements IEntityProcessingService {

    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Asteroid.class);

    @Override
    public void process(GameData data, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            if (asteroid.getPart(LifePart.class).isDead()) {
                world.removeEntity(asteroid);
                continue;
            }
            asteroid.getParts().forEach(p -> p.process(data,asteroid));
            constructor.updateShape(asteroid);
        }
    }
}












