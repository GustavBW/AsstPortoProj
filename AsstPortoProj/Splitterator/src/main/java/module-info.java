import guwan21.common.services.IEntityPostProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.splitterator.AsteroidFragmentationPlugin;
import guwan21.splitterator.AsteroidSplitteratorService;

module Splitterator {
    requires Common;
    requires Asteroid;

    provides IEntityPostProcessingService with AsteroidSplitteratorService;
    provides IGamePluginService with AsteroidFragmentationPlugin;
}