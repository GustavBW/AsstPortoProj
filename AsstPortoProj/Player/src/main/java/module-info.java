import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;

module Player {
    requires Common;

    uses IBulletCreator;

    provides IGamePluginService with guwan21.player.PlayerPlugin;
    provides IEntityProcessingService with guwan21.player.PlayerControlSystem;
}