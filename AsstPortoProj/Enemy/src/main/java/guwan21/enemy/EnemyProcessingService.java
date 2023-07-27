package guwan21.enemy;

import java.lang.Math;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.SPILocator;

public class EnemyProcessingService implements IEntityProcessingService {

    private float secondsFromGameStart = 0;

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

            secondsFromGameStart += data.getDelta();

            if(Math.floor(secondsFromGameStart) % 2 == 0){
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
                SPILocator.locateAll(IBulletCreator.class).forEach(bc -> bc.fire(enemy,world));
            }

            updateShape(enemy);
        }
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
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = (float) (Math.PI / 2f);

        float[] points = new float[7];
        points[0] = 1;
        points[1] = 2;
        points[2] = 2.2f;
        points[3] = 2.5f;
        points[4] = 2.8f;
        points[5] = 3;
        points[6] = 4;

        float[] distance = new float[7];
        distance[0] = distance[1] = distance[2] = 10;
        distance[3] = 15;
        distance[4] = distance[5] = distance[6] = 10;

        for (int i = 0; i < 7; i++) {
            shapex[i] = (float) (x + Math.cos(radians + Math.PI * (points[i] / 5)) * distance[i]);
            shapey[i] = (float) (y + Math.sin(radians + Math.PI * (points[i] / 5)) * distance[i]);
        }

        for (int i = 0; i < 7; i++) {
            shapex[i + 7] = (float) (x - Math.cos(radians + Math.PI * points[i] / 5) * distance[i]);
            shapey[i + 7] = (float) (y - Math.sin(radians + Math.PI * points[i] / 5) * distance[i]);
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}












