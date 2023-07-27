import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.splitterator.AsteroidFragmentationPlugin;
import guwan21.splitterator.AsteroidSplitteratorService;

module Splitterator {
    requires Common;
    requires Asteroid;

    provides IEntityProcessingService with AsteroidSplitteratorService;
    provides IGamePluginService with AsteroidFragmentationPlugin;
}