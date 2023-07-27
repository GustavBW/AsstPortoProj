package guwan21.splitterator;

import guwan21.asteroid.Asteroid;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.services.IEntityPostProcessingService;

public class AsteroidSplitteratorService implements IEntityPostProcessingService {

    private final AsteroidFragmentationPlugin asteroidPlugin = new AsteroidFragmentationPlugin();
    /**
     * For any Asteroid; split if hit
     * Pre-condition: Any asteroid  and has a lifepart
     * post-condition: Old instance is removed and 2 new, smaller instances added. If it has been hit.
     *
     * @param data Current game state
     * @param world Current world state
     */
    @Override
    public void process(GameData data, World world) {
        for(Entity asteroid : world.getEntities(Asteroid.class)){

            LifePart lifePart = asteroid.getPart(LifePart.class);

            if (lifePart.isHit() && !lifePart.isDead()) {
                continue;
            }

            asteroidPlugin.fracture(world, asteroid);
        }
    }
}
