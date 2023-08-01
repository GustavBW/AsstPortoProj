package guwan21.bullet;

import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.services.IEntityProcessingService;

public class BulletProcessingService implements IEntityProcessingService {

    private BulletPlugin plugin = new BulletPlugin();

    @Override
    public void process(GameData data, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            if (bullet.getPart(LifePart.class).isDead()) {
                world.removeEntity(bullet);
                continue;
            }

            bullet.getParts().forEach(bp -> bp.process(data, bullet));

            updateShape(bullet);
        }

        plugin.checkFiringEvents(data,world);
    }


    /**
     * Update the shape of entity
     * <br />
     * Pre-condition: An entity that can be drawn, and a game tick has passed since last call for entity <br />
     * Post-condition: Updated shape location for the entity
     *
     * @param entity Entity to update shape of
     */
    private void updateShape(Entity entity) {

    }
}












