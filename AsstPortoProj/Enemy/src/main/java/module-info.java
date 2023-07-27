import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;
import guwan21.enemy.EnemyProcessingService;
import guwan21.enemy.TimeBasedEnemyFactory;

module Enemy {
    requires Common;

    uses SPILocator;
    uses IBulletCreator;

    provides IGamePluginService with guwan21.enemy.EnemyPlugin;
    provides IEntityProcessingService with EnemyProcessingService;
    provides ITimeBasedEntityFactory with TimeBasedEnemyFactory;
}