package guwan21.splitterator;

import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.services.IEntityPreProcessingService;

public class AsteroidSplitteratorService implements IEntityPreProcessingService {

    private AsteroidFragmentationPlugin asteroidPlugin = new AsteroidFragmentationPlugin();

    public AsteroidSplitteratorService(){}
    //Package private constructor for testing purposes
    AsteroidSplitteratorService(AsteroidFragmentationPlugin plugin){
        this.asteroidPlugin = plugin;
    }


    /**
     * For any Asteroid; split if hit
     * Pre-condition: Any asteroid and has a lifepart
     * post-condition: A small fragment is added at the position of the damaged asteroid
     *
     * @param data Current game state
     * @param world Current world state
     */
    @Override
    public void process(GameData data, World world) {
        for(Entity asteroid : world.getEntities(Asteroid.class)){

            LifePart lp = asteroid.getPart(LifePart.class);
            if (lp.isHit() && lp.getLife() > 1) { //Since this is on the preprocessing pass, damage havent been applied yet
                world.addEntity(
                        asteroidPlugin.fracture(world, asteroid)
                );
            }
        }
    }
}
