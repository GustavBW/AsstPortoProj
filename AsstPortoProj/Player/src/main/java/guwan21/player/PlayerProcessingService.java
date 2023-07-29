package guwan21.player;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.GameKeys;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.SPILocator;

public class PlayerProcessingService implements IEntityProcessingService {
    @Override
    public void process(GameData data, World world) {
        for (Entity player : world.getEntities(Player.class)) {

            if (player.getPart(LifePart.class).isDead()) {
                world.removeEntity(player);
                continue;
            }

            MovingPart movingPart = player.getPart(MovingPart.class);
            WeaponPart weaponPart = player.getPart(WeaponPart.class);

            movingPart.setLeft(data.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(data.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(data.getKeys().isDown(GameKeys.UP));

            player.getParts().forEach(p -> p.process(data, player));

            weaponPart.setFiring(data.getKeys().isDown(GameKeys.SPACE));
            if (weaponPart.isFiring()) {
                SPILocator.locateBeans(IBulletCreator.class).forEach(bc -> bc.fire(player,world));
            }

            updateShape(player);
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
    }
}
