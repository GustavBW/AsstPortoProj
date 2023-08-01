module Bullet {
    requires Common;

    provides guwan21.common.services.IGamePluginService with guwan21.bullet.BulletPlugin;
    provides guwan21.common.services.IEntityProcessingService with guwan21.bullet.BulletProcessingService;
    provides guwan21.common.services.IEntityConstructionService with guwan21.bullet.BulletConstructionService;
}