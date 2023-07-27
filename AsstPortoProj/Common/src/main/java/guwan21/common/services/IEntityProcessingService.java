package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IEntityProcessingService {
    /**
     * Processes entities.
     * Pre-condition: A game tick has passed since last call.
     * Post-condition: The entity has been processed and updated.
     * 
     * @param data Current game state information
     * @param world Current world state
     */
    void process(GameData data, World world);
}
