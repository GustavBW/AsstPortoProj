package guwan21.collision;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IPostEntityProcessingService;

import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        List<Entity> entities1 = world.getEntities().stream().toList();
        List<Entity> entities2 = world.getEntities().stream().toList();

        for(int i = 0; i < entities1.size(); i++){
            for(int j = i; j < entities2.size(); j++){

                final Entity e1 = entities1.get(i);
                final Entity e2 = entities2.get(j);

                LifePart lpE1 = e1.getPart(LifePart.class);

                if(checkCollision(e1,e2) && lpE1.getLife() > 0){
                    ((LifePart) (e1.getPart(LifePart.class))).setIsHit(true);
                }
            }
        }

    }

    private boolean checkCollision(Entity e1, Entity e2) {
        // Get data for collision detection
        PositionPart e1Pos = e1.getPart(PositionPart.class);
        PositionPart e2Pos = e2.getPart(PositionPart.class);

        return checkCollision(
                e1Pos.getX(),
                e1Pos.getY(),
                e1.getRadius(),
                e2Pos.getX(),
                e2Pos.getY(),
                e2.getRadius()
        );
    }

    public boolean checkCollision(float e1x, float e1y, float e1r, float e2x, float e2y, float e2r) {
        float dx = e1x - e2x;
        float dy = e1y - e2y;
        float distanceBetweenSQ = dx*dx+dy*dy;
        //comparing square values as to avoid sqrt. Sqrt is the root of all evil.
        return distanceBetweenSQ < (e1r + e2r) * (e1r + e2r);
    }
}
