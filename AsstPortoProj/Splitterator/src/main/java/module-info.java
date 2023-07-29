import guwan21.common.services.IEntityPreProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.splitterator.AsteroidFragmentationPlugin;
import guwan21.splitterator.AsteroidSplitteratorService;

module Splitterator {
    requires Common;

    provides IEntityPreProcessingService with AsteroidSplitteratorService;
    provides IGamePluginService with AsteroidFragmentationPlugin;
}