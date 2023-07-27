import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.services.IEntityPostProcessingService;

module Common {
    exports guwan21.common.services;
    exports guwan21.common.data;
    exports guwan21.common.data.entityparts;
    exports guwan21.common.util;
    exports guwan21.common.factories;

    uses IBulletCreator;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IEntityPostProcessingService;
    uses ITimeBasedEntityFactory;
}