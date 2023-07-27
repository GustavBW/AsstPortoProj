package guwan21.asteroid;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.factories.ITimeBasedEntityFactory;

public class TimeBasedAsteroidFactory implements ITimeBasedEntityFactory {

    private final AsteroidConstructor constructor = new AsteroidConstructor();
    private float timeoutModifier = 1;

    @Override
    public int getTimeout() {
        return (int) (5000 * timeoutModifier);
    }

    @Override
    public Entity produce() {
        timeoutModifier *= 0.9f;
        return constructor.create();
    }

    @Override
    public Entity configure(Entity instance, GameData data, World world) {
        return constructor.adaptTo(instance, data,world);
    }

}
