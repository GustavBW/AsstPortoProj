package guwan21.common.factories;

import guwan21.common.data.entities.Entity;

public interface ITimeBasedEntityFactory extends AutomatedEntityFactory<Entity> {

    /**
     * Milliseconds between each factory invocation <br>
     * pre-condition: The factory has been invoked and it is time to calculate when to invoke it next<br>
     * post-condition: The amount of time in which the factory should lie dormant per invocation is provided (not a constant)
     */
    int getTimeout();


}
