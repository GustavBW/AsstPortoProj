module Enemy {
    requires Common;

    provides guwan21.common.services.IGamePluginService with guwan21.enemy.EnemyPlugin;
    provides guwan21.common.services.IEntityProcessingService with guwan21.enemy.EnemyProcessingService;
    provides guwan21.common.factories.ITimeBasedEntityFactory with guwan21.enemy.TimeBasedEnemyFactory;
    provides guwan21.common.services.IEntityConstructionService with guwan21.enemy.EnemyConstructionService;
}