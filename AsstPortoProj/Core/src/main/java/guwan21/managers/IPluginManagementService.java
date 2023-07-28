package guwan21.managers;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IPluginManagementService {
    void startPlugins(GameData data, World world);
    void stopPlugins(GameData data, World world);
}
