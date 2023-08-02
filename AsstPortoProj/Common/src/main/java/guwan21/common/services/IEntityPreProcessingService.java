package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IEntityPreProcessingService {
    /**
     * Pre-processing pass for entities (E.g. the Asteroid Splitterator)<br>
     * Pre-condition: The main processing pass is about to occur.<br>
     * Post-condition: Pre-processing complete - entities now ready for processing.<br>
     *<br>
     * @param data Current game state information<br>
     * @param world Current world state
     */
    void process(GameData data, World world);
}
