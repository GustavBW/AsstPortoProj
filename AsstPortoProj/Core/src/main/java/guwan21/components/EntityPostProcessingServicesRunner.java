package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IEntityPostProcessingService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

@Service
public class EntityPostProcessingServicesRunner implements IProcessor {
    @Override
    public void process(GameData data, World world) {
        for(IEntityPostProcessingService proc : SPILocator.locateAll(IEntityPostProcessingService.class)){
            proc.process(data,world);
        }
    }
}
