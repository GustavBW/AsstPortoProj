package guwan21.splitterator;

import guwan21.asteroid.Asteroid;
import guwan21.asteroid.AsteroidConstructor;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IGamePluginService;

public class AsteroidFragmentPlugin implements IGamePluginService {

    private final AsteroidConstructor constructor = new AsteroidConstructor();

    @Override
    public void start(GameData gameData, World world) {}

    @Override
    public void stop(GameData gameData, World world) {}

    /**
     * Pre-condition: An asteroid has been hit. Oh no!
     * Post-condition: Fragment added to world based on original asteroid.
     * @param world Current world state
     * @param asteroid Asteroid that has been split
     */
    protected void fracture(World world, Entity asteroid) {
        world.removeEntity(asteroid);

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        MovingPart movingPart = asteroid.getPart(MovingPart.class);
        LifePart lifePart = asteroid.getPart(LifePart.class);



        float[] splits = new float[] {(float) (Math.PI * 1/2), (float) (Math.PI * -1/2)};

        for (float split : splits) {
            Entity fragment = new Asteroid();

            constructor.setAsteroidRadius(fragment);

            float radians = positionPart.getRadians() + split;

            float bx = (float) Math.cos(radians) * asteroid.getRadius();
            float x = bx + positionPart.getX();
            float by = (float) Math.sin(radians) * asteroid.getRadius();
            float y = by + positionPart.getY();

            float currentSpeed = movingPart.getSpeed();
            float startSpeed = (float) ((Math.random() * (75f - currentSpeed)) + currentSpeed);

            constructor.build(fragment, x, y, radians, startSpeed);

            world.addEntity(fragment);
        }
    }
}
