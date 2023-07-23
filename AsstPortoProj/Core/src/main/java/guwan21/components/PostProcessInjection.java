package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IPostEntityProcessingService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PostProcessInjector")
public class PostProcessInjection implements IProcessor {
    @Override
    public void process(GameData data, World world) {
        for(IProcessor proc : SPILocator.locateAll(IPostEntityProcessingService.class)){
            proc.process(data,world);
        }
    }
}
