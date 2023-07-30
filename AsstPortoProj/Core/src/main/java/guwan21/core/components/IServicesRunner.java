package guwan21.core.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IServicesRunner {
    /**
     * Pre-condition: A services runner of some type of service is to be invoked
     * Post-condition: The Services runner has run
     *
     * @param data Current game state
     * @param world Current world state - inhabiting entities etc.
     */
    void process(GameData data, World world);
}
