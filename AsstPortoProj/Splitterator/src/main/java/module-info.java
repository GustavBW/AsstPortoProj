import guwan21.common.services.IEntityPostProcessingService;
import guwan21.common.services.IGamePluginService;

module Splitterator {
    requires Common;
    requires Asteroid;

    provides IEntityPostProcessingService with guwan21.splitterator.AsteroidSplitterator;
    provides IGamePluginService with guwan21.splitterator.AsteroidFragmentPlugin;
}