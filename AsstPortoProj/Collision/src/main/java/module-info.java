import guwan21.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;

    provides IPostEntityProcessingService with guwan21.collision.CollisionDetector;
}