package guwan21.enemy;

import java.lang.Math;

import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.SPILocator;

public class EnemyProcessingService implements IEntityProcessingService {

    private final EnemyConstructionService constructor = new EnemyConstructionService();

    @Override
    public void process(GameData data, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            WeaponPart weaponPart = enemy.getPart(WeaponPart.class);

            enemy.getParts().forEach(ep -> ep.process(data,enemy));
            if (enemy.getPart(LifePart.class).isDead()) {
                world.removeEntity(enemy);
                continue;
            }

            constructor.updateShape(enemy);

            if(Math.floor(data.getMsFromGameStart() % 2) == 0){
                final int rand = (int) (Math.random() * 3);
                movingPart.setUp(false);
                movingPart.setRight(false);
                movingPart.setLeft(false);
                weaponPart.setFiring(false);
                switch (rand){
                    case 0 -> movingPart.setLeft(true);
                    case 1 -> movingPart.setRight(true);
                    case 2 -> movingPart.setUp(true);
                }
                continue;
            }

            weaponPart.setFiring(true);
            if (weaponPart.isFiring()) {
                SPILocator.locateBeans(IBulletCreator.class).forEach(bc -> bc.fire(enemy,world));
            }

        }
    }
}












