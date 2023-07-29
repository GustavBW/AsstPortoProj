import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.SPILocator;

module Enemy {
    requires Common;

    uses SPILocator;
    uses IBulletCreator;

    provides IGamePluginService with guwan21.enemy.EnemyPlugin;
    provides IEntityProcessingService with guwan21.enemy.EnemyProcessingService;
    provides ITimeBasedEntityFactory with guwan21.enemy.TimeBasedEnemyFactory;
    provides IEntityConstructionService with guwan21.enemy.EnemyConstructionService;
}