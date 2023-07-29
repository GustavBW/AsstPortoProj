package guwan21.player;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;

public class PlayerPlugin implements IGamePluginService {

    private final IEntityConstructionService constructor = EntityConstructionServiceRegistry.getFor(Player.class);

    public PlayerPlugin() {

    }

    @Override
    public void start(GameData data, World world) {
        Entity player = constructor.create();
        constructor.adaptTo(player,data,world);
        world.addEntity(player);
    }


    @Override
    public void stop(GameData data, World world) {
        world.removeEntities(Player.class);
    }
}
