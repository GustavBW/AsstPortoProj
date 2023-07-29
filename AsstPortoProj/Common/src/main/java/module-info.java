import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.*;

module Common {
    exports guwan21.common.services;
    exports guwan21.common.data;
    exports guwan21.common.data.entityparts;
    exports guwan21.common.util;
    exports guwan21.common.factories;
    exports guwan21.common.data.entities;

    uses IBulletCreator;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IEntityPostProcessingService;
    uses ITimeBasedEntityFactory;
    uses IEntityConstructionService;
}