import guwan21.common.services.IEntityPostProcessingService;

module Collision {
    requires Common;

    provides IEntityPostProcessingService with guwan21.collision.CollisionDetector;
}