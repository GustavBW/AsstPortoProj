package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IGamePluginService {
    /**
     * Pre-condition: As part of game initialization phase. I.e. gameplay phase is about to begin
     * Post-condition: Initialization complete and any additions to world and / or game state resolved.
     * @param gameData Current game state
     * @param world Current world state
     */
    void start(GameData gameData, World world);

    /**
     * Pre-condition: As part of game exit / shut down
     * Post-condition: The plugin is no longer active and any removals of parts of game state or world state resolved.
     * @param gameData Current game state
     * @param world Current world state
     */
    void stop(GameData gameData, World world);
}
