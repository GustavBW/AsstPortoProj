package guwan21.bullet;

import guwan21.common.data.Color;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.entityparts.EntityPart;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IEntityConstructionService;

public class BulletConstructionService implements IEntityConstructionService {
    @Override
    public Entity create() {
        Entity bullet = new Bullet();

        bullet.setRadius(1);
        bullet.setShapeX(new float[4]);
        bullet.setShapeY(new float[4]);
        bullet.setColor(new Color(1,1,1,1));
        bullet.add(new MovingPart(0,0,1000,5, 400));
        bullet.add(new PositionPart(0, 0, 0));
        bullet.add(new LifePart(1, 3));

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
        float radians = positionPart.getRadians();

        shapeX[0] = (float) (x + Math.cos(radians) * 1);
        shapeY[0] = (float) (y + Math.sin(radians) * 1);

        shapeX[1] = (float) (x + Math.cos(radians) * 0);
        shapeY[1] = (float) (y + Math.sin(radians) * 0);

        shapeX[2] = (float) (x + Math.cos(radians) * 2);
        shapeY[2] = (float) (y + Math.sin(radians) * 2);

        shapeX[3] = (float) (x + Math.cos(radians) * -2);
        shapeY[3] = (float) (y + Math.sin(radians) * -2);

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
