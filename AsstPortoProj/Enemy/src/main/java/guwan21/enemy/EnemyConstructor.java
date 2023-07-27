package guwan21.enemy;

import guwan21.common.data.Color;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.data.entityparts.WeaponPart;

public class EnemyConstructor {

    /**
     * Pre-condition: New instance required
     * Post-condition: Enemy entity, that has default parameters and parts
     */
    public Entity create() {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = (float) Math.random();
        float y = (float) Math.random();
        float radians = (float) (Math.random() * 2 * Math.PI);

        Entity enemyShip = new Enemy();

        enemyShip.setRadius(10);

        enemyShip.setShapeX(new float[14]);
        enemyShip.setShapeY(new float[14]);
        enemyShip.setColor(new Color(1,0,0,1));
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed, 50));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1, 10_000_000));
        enemyShip.add(new WeaponPart(0.2f));

        return enemyShip;
    }

    public Entity adjust(Entity enemy, GameData data, World world){
        PositionPart pos = enemy.getPart(PositionPart.class);

        //Scale to viewport size
        pos.setX(pos.getX() * data.getDisplayWidth());
        pos.setY(pos.getY() * data.getDisplayHeight());
        return enemy;
    }

}
