package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IEntityPostProcessingService {
        /**
         * Post process pass for entities.
         * Pre-condition: Game tick and entity processing resolved.
         * Post-condition: Post processes applied.
         * @param data Current game state
         * @param world Current world state
         */
        void process(GameData data, World world);
}
