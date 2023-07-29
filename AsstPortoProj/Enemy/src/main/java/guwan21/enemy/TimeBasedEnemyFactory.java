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
                (float) data.getMsFromGameStart() / 1000, //x
                5000, //new instance every 5 seconds
                500, //new instance twice a second
                .02f,
                200 //At 200 seconds (>3 min) into the game, the rapid increase will ease out
        );
        return constructor.adaptTo(instance,data,world);
    }

    @Override
    public int getTimeout() {
        return (int) currentTimeout;
    }
}
