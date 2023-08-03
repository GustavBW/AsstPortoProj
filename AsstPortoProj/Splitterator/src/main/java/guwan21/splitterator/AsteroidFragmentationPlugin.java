package guwan21.splitterator;

import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;

public class AsteroidFragmentationPlugin implements IGamePluginService {

    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Asteroid.class);

    @Override
    public void start(GameData data, World world) {}

    @Override
    public void stop(GameData data, World world) {}

    /**
     * Pre-condition: An asteroid has been hit. Oh no!
     * Post-condition: Fragment added to world based on original asteroid.
     * @param world Current world state
     * @param parent Asteroid that has been split
     */
    protected Entity fracture(World world, Entity parent) {

        PositionPart parentPosition = parent.getPart(PositionPart.class);
        MovingPart parentMovement = parent.getPart(MovingPart.class);

        Asteroid fragment = (Asteroid) constructor.create();

        constructor.configure(fragment,
                new PositionPart(parentPosition.getX(), parentPosition.getY(), (float) (Math.random() * 2 * Math.PI)),
                new LifePart(1,1_000_000),
                new MovingPart(0,0,400,0,(float) ((Math.random() + .5) * parentMovement.getSpeed()))
                );

        constructor.updateShape(fragment);

        return fragment;
    }
}
