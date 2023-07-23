import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;

module Enemy {
    requires Common;

    uses SPILocator;
    uses IBulletCreator;

    provides IGamePluginService with guwan21.enemy.EnemyPlugin;
    provides IEntityProcessingService with guwan21.enemy.EnemyControlSystem;
}