package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MainInjector")
public class PluginInjection {

    public void startPlugins(GameData gameData, World world) {
        for (IProcessor proc : SPILocator.locateAll(IGamePluginService.class)){
            proc.process(gameData,world);
        }
    }
}
