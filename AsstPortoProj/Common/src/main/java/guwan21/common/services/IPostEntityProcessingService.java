package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IPostEntityProcessingService  {
        /**
         * Post process pass for entities.
         * Pre-condition: Game tick and entity processing resolved.
         * Post-condition: Post processes applied.
         * @param gameData Current game state
         * @param world Current world state
         */
        void process(GameData gameData, World world);
}
