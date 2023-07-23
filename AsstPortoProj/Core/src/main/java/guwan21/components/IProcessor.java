package guwan21.components;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IProcessor {
    /**
     * @param data Current game state
     * @param world Current world state - inhabiting entities etc.
     */
    void process(GameData data, World world);
}
