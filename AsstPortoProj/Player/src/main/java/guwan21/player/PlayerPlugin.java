package guwan21.player;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    public PlayerPlugin() {

    }

    @Override
    public void start(GameData data, World world) {
        Entity player = createPlayer(data);
        world.addEntity(player);
    }

    /**
     * Create player
     * Pre-condition: No playable character exists
     * Post-condition: A playable character exists
     *
     * @param gameData Current game state
     * @return Player entity
     */
    private Entity createPlayer(GameData gameData) {
        Entity player = new Player();

        player.setRadius(8);

        player.add(new MovingPart(
                10,
                200,
                300,
                5,
                0
        ));
        player.add(new PositionPart(
                gameData.getDisplayWidth() / 2f,
                gameData.getDisplayHeight() / 2f,
                (float) Math.PI / 2
        ));
        player.add(new LifePart(1, 0));
        player.add(new WeaponPart(0.2f));

        return player;
    }

    @Override
    public void stop(GameData data, World world) {
        world.removeEntities(Player.class);
    }
}
