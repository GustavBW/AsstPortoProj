package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;

public interface IEntityPostProcessingService {
        /**
         * Post process pass for entities.<br>
         * Pre-condition: Game tick and entity processing resolved.<br>
         * Post-condition: Post processes applied.<br>
         * @param data Current game state<br>
         * @param world Current world state
         */
        void process(GameData data, World world);
}
