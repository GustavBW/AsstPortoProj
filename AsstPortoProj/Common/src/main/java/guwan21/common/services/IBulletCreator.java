package guwan21.common.services;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IBulletCreator {
    /**
     * Start the plugin.
     * Pre-condition: Game running and shooter wants bullet to appear.
     * Post-condition: Bullet entity that is ready to be added to the game world.
     *
     * @param shooter Game entity
     * @param gameData Game state
     */
    Entity create(Entity shooter, GameData gameData);
}
