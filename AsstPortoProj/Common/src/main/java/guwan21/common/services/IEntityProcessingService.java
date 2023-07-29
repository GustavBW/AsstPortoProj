package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IEntityProcessingService {
    /**
     * Main entity processing-pass.
     * Pre-condition: A game tick has passed since last call.
     * Post-condition: All entities processed and scene graph updated.
     * 
     * @param data Current game state information
     * @param world Current world state
     */
    void process(GameData data, World world);
}
