package guwan21.bullet;

import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.EntityConstructionServiceRegistry;

public class BulletProcessingService implements IEntityProcessingService {

    private final IEntityConstructionService constructionService = EntityConstructionServiceRegistry.getFor(Bullet.class);

    @Override
    public void process(GameData data, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            if (bullet.getPart(LifePart.class).isDead()) {
                world.removeEntity(bullet);
                continue;
            }

            bullet.getParts().forEach(bp -> bp.process(data, bullet));

            constructionService.updateShape(bullet);
        }
    }
}












