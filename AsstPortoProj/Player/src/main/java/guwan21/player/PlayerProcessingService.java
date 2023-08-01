package guwan21.player;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.GameKeys;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.events.Event;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;
import guwan21.common.util.SPILocator;

import java.util.Collection;

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
            //T source, Type type, Category category, Target target
            if (weaponPart.isFiring()) {
                data.getBroker().addEvent(
                        new Event<>(player, Event.Type.INSTANT, Event.Category.GAMEPLAY, Event.Target.SERVICE)
                                .setTargetType(IGamePluginService.class)
                );
            }

            constructor.updateShape(player);
        }
    }
}
