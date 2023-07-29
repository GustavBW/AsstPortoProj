package guwan21.common.services;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.World;

public interface IBulletCreator {
    /**
     * Pre-condition: Game running and shotOrigin wants bullet to appear.
     * Post-condition: Bullet entity that is ready to be added to the game world.
     *
     * @param shotOrigin Game entity
     */
    void fire(Entity shotOrigin, World world);
}
