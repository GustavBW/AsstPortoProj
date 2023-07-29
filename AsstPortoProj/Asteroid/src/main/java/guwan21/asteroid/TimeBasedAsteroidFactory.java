package guwan21.asteroid;

import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.util.EntityConstructionServiceRegistry;
import guwan21.common.util.Sigmoid;

public class TimeBasedAsteroidFactory implements ITimeBasedEntityFactory {

    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Asteroid.class);

    private double currentTimeout = 5000;

    @Override
    public int getTimeout() {
        return (int) currentTimeout;
    }

    @Override
    public Entity produce() {
        return constructor.create();
    }

    @Override
    public Entity configure(Entity instance, GameData data, World world) {
        currentTimeout = Sigmoid.inverted(
                (float) data.getMsFromGameStart() / 1000,
                5000, //new instance every 5 seconds
                500, //new instance twice a second
                .02f,
                200 //At 200 seconds (>3 min) into the game, the rapid increase will ease out
        );

        MovingPart currentMvmnt = instance.getPart(MovingPart.class);
        float speedIncrease = (float) (data.getMsFromGameStart() / 1000f);
        MovingPart movementWithCorrectedSpeed = new MovingPart(
                currentMvmnt.getDeceleration(),
                currentMvmnt.getAcceleration(),
                //Increasing the max speed by 2u/s every second
                currentMvmnt.getMaxSpeed() + (speedIncrease * 2),
                currentMvmnt.getRotationSpeed(),
                //Increasing the start speed by 1u/s every second
                currentMvmnt.getStartSpeed() + speedIncrease
        );

        constructor.configure(
                instance,
                movementWithCorrectedSpeed
        );

        return constructor.adaptTo(instance, data, world);
    }

}
