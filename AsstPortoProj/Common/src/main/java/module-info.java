import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.services.IPostEntityProcessingService;

module Common {
    exports guwan21.common.services;
    exports guwan21.common.data;
    exports guwan21.common.data.entityparts;
    exports guwan21.common.util;

    uses IBulletCreator;
    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
}