import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;

module Asteroid {
    requires Common;

    provides IGamePluginService with guwan21.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with guwan21.asteroid.AsteroidControlSystem;
}