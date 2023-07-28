package guwan21.common.data.entityparts;

import guwan21.common.data.GameData;
import guwan21.common.data.entities.Entity;

public interface EntityPart {
    void process(GameData gameData, Entity entity);
}
