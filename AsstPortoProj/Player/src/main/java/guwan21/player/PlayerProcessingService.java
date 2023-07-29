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
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.EntityConstructionServiceRegistry;
import guwan21.common.util.SPILocator;

public class PlayerProcessingService implements IEntityProcessingService {

    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Player.class);


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

            constructor.updateShape(player);
        }
    }
}
