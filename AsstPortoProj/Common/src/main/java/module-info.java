module Common {
    exports guwan21.common.services;
    exports guwan21.common.data;
    exports guwan21.common.data.entityparts;
    exports guwan21.common.util;
    exports guwan21.common.factories;
    exports guwan21.common.data.entities;

    uses guwan21.common.services.IBulletCreator;
    uses guwan21.common.services.IGamePluginService;
    uses guwan21.common.services.IEntityPreProcessingService;
    uses guwan21.common.services.IEntityProcessingService;
    uses guwan21.common.services.IEntityPostProcessingService;
    uses guwan21.common.factories.ITimeBasedEntityFactory;
    uses guwan21.common.services.IEntityConstructionService;
    uses guwan21.common.bootloader.Initializable;

    provides guwan21.common.bootloader.Initializable with
            guwan21.common.util.EntityConstructionServiceRegistry,
            guwan21.common.util.SPILocator;
}