package guwan21.enemy;

import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.util.EntityConstructionServiceRegistry;
import guwan21.common.util.Sigmoid;

public class TimeBasedEnemyFactory implements ITimeBasedEntityFactory {

    private double currentTimeout = 5000;
    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Enemy.class);

    @Override
    public Entity produce() {
        return constructor.create();
    }

    @Override
    public Entity configure(Entity instance, GameData data, World world) {
        currentTimeout = Sigmoid.inverted(
                (float) data.getMsFromGameStart(), //x
                5000,
                500,
                .02f,
                200
        );
        return constructor.adaptTo(instance,data,world);
    }

    @Override
    public int getTimeout() {
        return (int) currentTimeout;
    }
}
