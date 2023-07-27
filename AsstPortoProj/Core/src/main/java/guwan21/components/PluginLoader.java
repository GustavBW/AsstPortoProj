package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

@Service
public class PluginLoader {

    public void startPlugins(GameData gameData, World world) {
        for (IGamePluginService plugin : SPILocator.locateAll(IGamePluginService.class)){
            plugin.start(gameData,world);
        }
    }

    public void stopPlugins(GameData data, World world){
        for(IGamePluginService plugin : SPILocator.locateAll(IGamePluginService.class)){
            plugin.stop(data, world);
        }
    }
}