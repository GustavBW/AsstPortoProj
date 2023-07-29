package guwan21.managers;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IPluginManagementService {

    /**
     * Pre-condition: Plugins have yet to be located and started
     * Post-condition: Installed plugins have been located and started
     * @param data Current game state
     * @param world Current world state
     */
    void startPlugins(GameData data, World world);

    /**
     * Pre-condition: Currently installed plugins are unknown, and has to be located and stopped
     * Post-condition: Installed plugins have been located and stopped
     * @param data Current game state
     * @param world Current world state
     */
    void stopPlugins(GameData data, World world);
}
