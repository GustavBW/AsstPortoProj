package guwan21.enemy;

import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;

public class EnemyPlugin implements IGamePluginService {

    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Enemy.class);

    public EnemyPlugin() {}

    @Override
    public void start(GameData data, World world) {
        Entity entity = constructor.create();
        constructor.adaptTo(entity,data,world);
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
