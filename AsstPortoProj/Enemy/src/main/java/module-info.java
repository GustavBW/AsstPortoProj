import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;
import guwan21.enemy.EnemyProcessingService;

module Enemy {
    requires Common;

    uses SPILocator;
    uses IBulletCreator;

    provides IGamePluginService with guwan21.enemy.EnemyPlugin;
    provides IEntityProcessingService with EnemyProcessingService;
}