package guwan21.common.factories;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface AutomatedEntityFactory<T extends Entity> {

    /**
     * Pre-condition: A new instance of type T is needed.
     * Post-condition: A new instance of type T is provided.
     */
    T produce();

    /**
     * Pre-condition: A presumably non-functional / incomplete instance
     * Post-condition: A quite operational instance of T
     */
    T configure(T instance, GameData data, World world);
}
