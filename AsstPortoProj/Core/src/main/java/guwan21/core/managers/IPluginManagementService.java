package guwan21.core.managers;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IPluginManagementService {

    /**
     * Pre-condition: Plugins have yet to be located and started<br>
     * Post-condition: Installed plugins have been located and started<br>
     * @param data Current game state<br>
     * @param world Current world state
     */
    void startPlugins(GameData data, World world);

    /**
     * Pre-condition: Currently installed plugins are unknown, and has to be located and stopped<br>
     * Post-condition: Installed plugins have been located and stopped<br>
     * @param data Current game state<br>
     * @param world Current world state
     */
    void stopPlugins(GameData data, World world);
}
