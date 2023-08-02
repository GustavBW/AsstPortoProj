package guwan21.bullet;

import guwan21.common.data.Color;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.*;
import guwan21.common.data.entityparts.*;
import guwan21.common.services.IEntityConstructionService;

public class BulletConstructionService implements IEntityConstructionService {
    @Override
    public Entity create() {
        Entity bullet = new Bullet();

        bullet.setRadius(2);
        bullet.setShapeX(new float[8]);
        bullet.setShapeY(new float[8]);
        bullet.setColor(new Color(1,1,1,1));
        bullet.add(new MovingPart(0,0,1000,5, 400));
        bullet.add(new PositionPart(0, 0, 0));
        bullet.add(new LifePart(1, 3));
        bullet.add(new EnemyRecord(Asteroid.class, Enemy.class, Player.class));

        return bullet;
    }

    @Override
    public Entity adaptTo(Entity entity, GameData data, World world) {
        return entity;
    }

    @Override
    public Entity updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float size = entity.getRadius();

        float angleIncrementRad = (float) ((Math.PI * 2) / shapeX.length);

        for(int i = 0; i < shapeX.length; i++){
            shapeX[i] = (float) Math.cos(angleIncrementRad * i) * size + x;
            shapeY[i] = (float) Math.sin(angleIncrementRad * i) * size + y;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
        return entity;
    }

    @Override
    public Entity configure(Entity entity, EntityPart... parts) {
        for(EntityPart part : parts) entity.add(part);
        return entity;
    }
}
