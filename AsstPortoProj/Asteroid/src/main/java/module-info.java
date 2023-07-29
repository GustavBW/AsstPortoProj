module Asteroid {
    requires Common;

    provides guwan21.common.services.IGamePluginService with guwan21.asteroid.AsteroidPlugin;
    provides guwan21.common.services.IEntityProcessingService with guwan21.asteroid.AsteroidProcessingService;
    provides guwan21.common.factories.ITimeBasedEntityFactory with guwan21.asteroid.TimeBasedAsteroidFactory;
    provides guwan21.common.services.IEntityConstructionService with guwan21.asteroid.AsteroidConstructionService;
}