package guwan21.player;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.*;
import guwan21.common.services.IEntityConstructionService;

public class PlayerConstructionService implements IEntityConstructionService {

    @Override
    public Entity create() {
        Entity player = new Player();

        player.setRadius(8);

        player.add(new MovingPart(
                10,
                200,
                300,
                5,
                0
        ));
        player.add(new PositionPart(.5f, .5f, (float) Math.PI / 2));
        player.add(new LifePart(3, 10_000_00));
        player.add(new WeaponPart(0.2f));

        return player;
    }

    @Override
    public Entity adaptTo(Entity entity, GameData data, World world) {
        PositionPart pp = entity.getPart(PositionPart.class);
        pp.setPosition(
                pp.getX() * data.getDisplayWidth(),
                pp.getY() * data.getDisplayHeight()
        );

        return entity;
    }

    @Override
    public Entity updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

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
