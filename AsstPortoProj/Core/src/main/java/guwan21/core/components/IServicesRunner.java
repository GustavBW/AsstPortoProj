package guwan21.core.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IServicesRunner {
    /**
     * Pre-condition: All services of some type needs to be invoked<br>
     * Post-condition: The Services runner has run and invoked all provided services<br>
     *<br>
     * @param data Current game state<br>
     * @param world Current world state - inhabiting entities etc.
     */
    void process(GameData data, World world);
}
