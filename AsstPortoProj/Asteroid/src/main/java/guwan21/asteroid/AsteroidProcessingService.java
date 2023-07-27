package guwan21.asteroid;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IEntityProcessingService;

/**
 * Asteroid control system, that control the movement and behavior of all asteroids.
 */
public class AsteroidProcessingService implements IEntityProcessingService {

    @Override
    public void process(GameData data, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            asteroid.getParts().forEach(p -> p.process(data,asteroid));
            if (asteroid.getPart(LifePart.class).isDead()) {
                world.removeEntity(asteroid);
                continue;
            }
            updateShape(asteroid);
        }
    }

    private final float[] baseShapeOffsets = {15, 35, 18, 11, 23, 12, 26, 25};

    /**
     * Pre-condition: An asteroid entity has been updated following a game tick
     * Post-condition: The asteroids graphical extends has been updated
     */
    private void updateShape(Entity entity) {
        float[] shapeX = new float[entity.getShapeX().length];
        float[] shapeY = new float[entity.getShapeY().length];
        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        int hp = entity.getPart(LifePart.class).getLife();

        for (int i = 0; i < shapeX.length && i < shapeY.length; i++) {
            float centerX = (float) (x + Math.cos(radians + Math.PI * (i / 4f)));
            float centerY = (float) (y + Math.sin(radians + Math.PI * (i / 4f)));

            shapeX[i] = centerX * (baseShapeOffsets[i] / (4 - hp));
            shapeY[i] = centerY * (baseShapeOffsets[i] / (4 - hp));
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
}












