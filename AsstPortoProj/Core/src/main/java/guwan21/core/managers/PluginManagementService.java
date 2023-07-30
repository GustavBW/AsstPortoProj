package guwan21.core.managers;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

@Service
public class PluginManagementService implements IPluginManagementService {

    public void startPlugins(GameData gameData, World world) {
        for (IGamePluginService plugin : SPILocator.getBeans(IGamePluginService.class)){
            plugin.start(gameData,world);
            System.out.println("\t[PluginLoader] started plugin: " + plugin.getClass());
        }
    }

    public void stopPlugins(GameData data, World world){
        for(IGamePluginService plugin : SPILocator.getBeans(IGamePluginService.class)){
            plugin.stop(data, world);
            System.out.println("\t[PluginLoader] stopped plugin: " + plugin.getClass());
        }
    }
}
