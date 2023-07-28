package guwan21.asteroid;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.util.Sigmoid;

public class TimeBasedAsteroidFactory implements ITimeBasedEntityFactory {

    private final AsteroidConstructor constructor = new AsteroidConstructor();
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
                (float) data.getMsFromGameStart(),
                5000,
                500,
                .02f,
                200
        );
        return constructor.adaptTo(instance, data,world);
    }

}
