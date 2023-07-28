package guwan21.common.factories;

import guwan21.common.data.entities.Entity;

public interface ITimeBasedEntityFactory extends AutomatedEntityFactory<Entity> {

    /**
     * Milliseconds between each factory invocation
     */
    int getTimeout();


}
