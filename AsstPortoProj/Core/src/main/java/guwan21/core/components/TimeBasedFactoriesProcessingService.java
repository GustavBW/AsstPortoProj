package guwan21.core.components;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TimeBasedFactoriesProcessingService implements ITimeBasedFactoriesProcessingService {

    private final Map<ITimeBasedEntityFactory,Long> factoryNextInvocationTimestampMap = new HashMap<>();

    @Override
    public void process(GameData data, World world) {
        for(ITimeBasedEntityFactory factory : SPILocator.locateBeans(ITimeBasedEntityFactory.class)){
            Long timestamp = factoryNextInvocationTimestampMap.get(factory);
            if(timestamp == null || timestamp <= System.currentTimeMillis()){
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
