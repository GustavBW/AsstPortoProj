package guwan21.components;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AutomatedFactoriesProcessingService implements IProcessor {

    private final Map<ITimeBasedEntityFactory,Long> factoryNextInvocationTimestampMap = new HashMap<>();

    @Override
    public void process(GameData data, World world) {
        for(ITimeBasedEntityFactory factory : SPILocator.locateAll(ITimeBasedEntityFactory.class)){
            if(factoryNextInvocationTimestampMap.get(factory) == null || factoryNextInvocationTimestampMap.get(factory) <= System.currentTimeMillis()){
                Entity entity = invokeFactory(factory,data,world);
                updateTimestampsFor(factory);
                world.addEntity(entity);
            }
        }
    }

    private Entity invokeFactory(ITimeBasedEntityFactory factory, GameData data, World world){
        Entity instance = factory.produce();
        factory.configure(instance,data,world);
        return instance;
    }

    private void updateTimestampsFor(ITimeBasedEntityFactory factory){
        factoryNextInvocationTimestampMap.put(factory, factory.getTimeout() + System.currentTimeMillis());
    }
}
