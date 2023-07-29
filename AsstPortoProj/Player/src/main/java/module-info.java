import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.player.PlayerConstructionService;
import guwan21.player.PlayerProcessingService;

module Player {
    requires Common;

    uses IBulletCreator;

    provides IGamePluginService with guwan21.player.PlayerPlugin;
    provides IEntityProcessingService with PlayerProcessingService;
    provides IEntityConstructionService with PlayerConstructionService;
}