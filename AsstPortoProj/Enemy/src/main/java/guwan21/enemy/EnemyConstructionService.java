package guwan21.enemy;

import guwan21.common.data.Color;
import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.*;
import guwan21.common.services.IEntityConstructionService;

public class EnemyConstructionService implements IEntityConstructionService {

    float baseDecellaration = 10, acceleration = 200, maxSpeed = 300, rotationSpeed = 5, baseRadius = 10;

    /**
     * Pre-condition: New instance required
     * Post-condition: Enemy entity, that has default parameters and parts
     */
    @Override
    public Entity create() {

        float x = (float) Math.random();
        float y = (float) Math.random();
        float radians = (float) (Math.random() * 2 * Math.PI);

        Entity enemyShip = new Enemy();

        enemyShip.setRadius(baseRadius);

        enemyShip.setShapeX(new float[14]);
        enemyShip.setShapeY(new float[14]);
        enemyShip.setColor(new Color(1,.1f,.1f,1));
        enemyShip.add(new MovingPart(baseDecellaration, acceleration, maxSpeed, rotationSpeed, 50));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1, 10_000_000));
        enemyShip.add(new WeaponPart(0.2f));

        return enemyShip;
    }

    @Override
    public Entity adaptTo(Entity enemy, GameData data, World world){
        PositionPart pos = enemy.getPart(PositionPart.class);

        //Scale to viewport size
        pos.setX(pos.getX() * data.getDisplayWidth());
        pos.setY(pos.getY() * data.getDisplayHeight());
        return enemy;
    }


    private final float[] baseShapeOffsets = new float[]{0.33f, 0.705f,0.33f,1};

    /**
     * Update the shape of entity
     * <br />
     * Pre-condition: An entity that can be drawn, and a game tick has passed since last call for entity <br />
     * Post-condition: Updated shape location for the entity
     *
     * @param entity Entity to update shape of
     */
    @Override
    public Entity updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        float radians = positionPart.getRadians();
        float radius = entity.getRadius();
        float radAngleIncrement = (float) ((2 * Math.PI) / shapex.length);

        for(int i = 0; i < shapex.length; i++){
            float xProjection = (float) Math.cos(i * radAngleIncrement + radians);
            float yProjection = (float) Math.sin(i * radAngleIncrement + radians);

            float offset = baseShapeOffsets[i % 4];

            shapex[i] = x + (offset * radius * xProjection);
            shapey[i] = y + (offset * radius * yProjection);
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
        return entity;
    }

    @Override
    public Entity configure(Entity entity, EntityPart... parts) {
        for(EntityPart part : parts) entity.add(part);
        return entity;
    }

}
