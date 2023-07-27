import guwan21.asteroid.AsteroidProcessingService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;

module Asteroid {
    exports guwan21.asteroid;
    requires Common;

    provides IGamePluginService with guwan21.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with AsteroidProcessingService;
}