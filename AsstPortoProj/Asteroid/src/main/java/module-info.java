import guwan21.asteroid.AsteroidProcessingService;
import guwan21.asteroid.TimeBasedAsteroidFactory;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.factories.ITimeBasedEntityFactory;

module Asteroid {
    exports guwan21.asteroid;
    requires Common;

    provides IGamePluginService with guwan21.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with AsteroidProcessingService;
    provides ITimeBasedEntityFactory with TimeBasedAsteroidFactory;
}