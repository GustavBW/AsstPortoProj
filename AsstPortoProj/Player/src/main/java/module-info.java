module Player {
    requires Common;

    provides guwan21.common.services.IGamePluginService with guwan21.player.PlayerPlugin;
    provides guwan21.common.services.IEntityProcessingService with guwan21.player.PlayerProcessingService;
    provides guwan21.common.services.IEntityConstructionService with guwan21.player.PlayerConstructionService;
}