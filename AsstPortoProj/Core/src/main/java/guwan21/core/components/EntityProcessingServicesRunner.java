package guwan21.core.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Component;

@Component
public class EntityProcessingServicesRunner implements IEntityProcessingServicesRunner {
    @Override
    public void process(GameData data, World world) {
        for(IEntityProcessingService proc : SPILocator.locateBeans(IEntityProcessingService.class)){
            proc.process(data,world);
        }
    }
}
