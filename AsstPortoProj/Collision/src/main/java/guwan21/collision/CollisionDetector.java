package guwan21.collision;

import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IEntityPostProcessingService;

import java.util.List;

public class CollisionDetector implements IEntityPostProcessingService {
    @Override
    public void process(GameData data, World world) {

        List<Entity> entities1 = world.getEntities().stream().toList();

        for(int i = 0; i < entities1.size(); i++){
            for(int j = i + 1; j < entities1.size(); j++){ //quick reduction from O(n^2) to O(n)

                final Entity e1 = entities1.get(i);
                final Entity e2 = entities1.get(j);

                if(e1.getID().equals(e2.getID())) continue;

                LifePart e1LP = e1.getPart(LifePart.class);
                LifePart e2LP = e2.getPart(LifePart.class);

                boolean e1CanGetHitBye2 = e1LP.verifyHit(e2,e1);
                boolean e2CanGetHitBye1 = e2LP.verifyHit(e1,e2);

                if(!(e1CanGetHitBye2 || e2CanGetHitBye1)) continue;

                if(isColliding(e1,e2)){
                    e1LP.setIsHit(e1CanGetHitBye2);
                    e2LP.setIsHit(e2CanGetHitBye1);
                }
            }
        }

    }

    private boolean isColliding(Entity e1, Entity e2) {
        PositionPart e1Pos = e1.getPart(PositionPart.class);
        PositionPart e2Pos = e2.getPart(PositionPart.class);

        return withinBounds(
                e1Pos.getX(),
                e1Pos.getY(),
                e1.getRadius(),
                e2Pos.getX(),
                e2Pos.getY(),
                e2.getRadius()
        );
    }

    public boolean withinBounds(float e1x, float e1y, float e1r, float e2x, float e2y, float e2r) {
        float dx = e1x - e2x;
        float dy = e1y - e2y;
        float distanceBetweenSQ = dx*dx+dy*dy;
        //comparing square values as to avoid sqrt. Sqrt is the root of all evil.
        return distanceBetweenSQ <= (e1r + e2r) * (e1r + e2r);
    }
}
