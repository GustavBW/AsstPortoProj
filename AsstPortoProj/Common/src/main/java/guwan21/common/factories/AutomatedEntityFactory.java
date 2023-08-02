package guwan21.common.factories;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface AutomatedEntityFactory<T extends Entity> {

    /**
     * Pre-condition: A new instance of type T is needed.<br>
     * Post-condition: A new instance of type T is provided.<br>
     */
    T produce();

    /**
     * Pre-condition: A presumably non-functional / incomplete instance<br>
     * Post-condition: A fully operational instance of T<br>
     */
    T configure(T instance, GameData data, World world);
}
