package guwan21.enemy;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.factories.ITimeBasedEntityFactory;

public class TimeBasedEnemyFactory implements ITimeBasedEntityFactory {

    private float timeoutModifier = 1;
    private final EnemyConstructor constructor = new EnemyConstructor();

    @Override
    public Entity produce() {
        timeoutModifier *= 0.9;
        return constructor.create();
    }

    @Override
    public Entity configure(Entity instance, GameData data, World world) {
        return constructor.adjust(instance,data,world);
    }

    @Override
    public int getTimeout() {
        return (int) (5000 * timeoutModifier);
    }
}
