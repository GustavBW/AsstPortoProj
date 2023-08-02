package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IGamePluginService {
    /**
     * Pre-condition: As part of game initialization phase. I.e. gameplay phase is about to begin<br>
     * Post-condition: Initialization complete and any additions to world and / or game state is resolved.<br>
     * @param data Current game state<br>
     * @param world Current world state
     */
    void start(GameData data, World world);

    /**
     * Pre-condition: As part of game exit / shut down<br>
     * Post-condition: The plugin is no longer active and any removals of parts of game state or world state is resolved.<br>
     * @param data Current game state<br>
     * @param world Current world state
     */
    void stop(GameData data, World world);
}
