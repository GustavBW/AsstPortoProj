package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IEntityPostProcessingService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Component;

@Component
public class EntityPostProcessingServicesRunner implements IEntityPostProcessingServicesRunner {
    @Override
    public void process(GameData data, World world) {
        for(IEntityPostProcessingService proc : SPILocator.locateBeans(IEntityPostProcessingService.class)){
            proc.process(data,world);
        }
    }
}
