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

public class AsteroidFragmentationPlugin implements IGamePluginService {

    private final AsteroidConstructor constructor = new AsteroidConstructor();

    @Override
    public void start(GameData data, World world) {}

    @Override
    public void stop(GameData data, World world) {}

    /**
     * Pre-condition: An asteroid has been hit. Oh no!
     * Post-condition: Fragment added to world based on original asteroid.
     * @param world Current world state
     * @param asteroid Asteroid that has been split
     */
    protected Entity fracture(World world, Entity asteroid) {

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        MovingPart movingPart = asteroid.getPart(MovingPart.class);

        Entity fragment = new Asteroid();

        float radians = (float) (positionPart.getRadians() + Math.PI);

        float startSpeed = (float) ((Math.random() + .5) * movingPart.getSpeed());

        constructor.build(fragment, positionPart.getX(), positionPart.getY(), radians, startSpeed,1);

        System.out.println("Asteroid fractured");
        return fragment;
    }
}
