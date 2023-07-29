import guwan21.bullet.BulletConstructionService;
import guwan21.bullet.BulletProcessingService;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;

module Bullet {
    requires Common;

    provides IBulletCreator with guwan21.bullet.BulletPlugin;
    provides IGamePluginService with guwan21.bullet.BulletPlugin;
    provides IEntityProcessingService with BulletProcessingService;
    provides IEntityConstructionService with BulletConstructionService;
}