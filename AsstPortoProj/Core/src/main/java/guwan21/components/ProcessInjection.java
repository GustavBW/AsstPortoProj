package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SubProcessInjector")
public class ProcessInjection implements IProcessor {
    @Override
    public void process(GameData data, World world) {
        List<IEntityProcessingService> processors = SPILocator.locateAll(IEntityProcessingService.class);
        for(IProcessor proc : processors){
            proc.process(data,world);
        }
    }
}
