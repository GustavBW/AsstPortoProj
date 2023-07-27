package guwan21.common.factories;

import guwan21.common.data.Entity;
import guwan21.common.factories.AutomatedEntityFactory;

public interface ITimeBasedEntityFactory extends AutomatedEntityFactory<Entity> {

    /**
     * Milliseconds between each factory invocation
     */
    int getTimeout();


}
