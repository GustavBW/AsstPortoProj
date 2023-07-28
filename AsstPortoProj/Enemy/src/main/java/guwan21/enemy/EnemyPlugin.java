package guwan21.enemy;

import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private final EnemyConstructor constructor = new EnemyConstructor();

    public EnemyPlugin() {

    }

    @Override
    public void start(GameData data, World world) {
        Entity entity = constructor.create();
        constructor.adjust(entity,data,world);
        world.addEntity(entity);
    }



    @Override
    public void stop(GameData data, World world) {
        // Remove entities
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }

}
