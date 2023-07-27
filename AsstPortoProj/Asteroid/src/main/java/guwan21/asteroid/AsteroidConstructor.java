package guwan21.asteroid;

import guwan21.common.data.Color;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;

public class AsteroidConstructor {

    private final int defaultMeshSize = 8, baseHitPoint = 3;
    private final Color defaultColor = new Color(1,1,1,1);

    /**
     * Pre-condition: Asteroid entity<br />
     * Post-condition: Asteroid entity with correct radius
     *
     * @param asteroid Asteroid entity to have its radius updated
     */
    public void setAsteroidRadius(Entity asteroid) {
        asteroid.setRadius(asteroid.getPart(LifePart.class).getLife() * 15);
    }


    /**
     * Create initial asteroid
     * Pre-condition: An asteroid is to be created;
     * Post-condition: A new asteroid instance
     * @return new instance
     */
    public Entity create() {

        Entity asteroid = new Asteroid();

        this.build(
                asteroid,
                (float) Math.random(),
                (float) Math.random(),
                (float) (Math.random() * (2 * Math.PI)),
                (float) (Math.random() * 50f) + 25f
        );

        return asteroid;
    }

    public Entity adaptTo(Entity asteroid, GameData data, World world){
        PositionPart pos = asteroid.getPart(PositionPart.class);

        //Scale to viewport size
        pos.setX(pos.getX() * data.getDisplayWidth());
        pos.setY(pos.getY() * data.getDisplayHeight());
        return asteroid;
    }

    //Describes a continuous hull as normalized distances from a center
    private final float[] normalizedShapeOffsets = {.6f, .4f, .72f, .41f, .96f, .43f, 1, 1};

    /**
     * Pre-condition: An asteroid entity has been updated following a game tick
     * Post-condition: The asteroids graphical extends has been updated
     */
    public void updateShape(Entity entity) {
        float[] shapeX = new float[entity.getShapeX().length];
        float[] shapeY = new float[entity.getShapeY().length];
        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float radius = entity.getRadius();

        float radAngleIncrement = (float) ((2 * Math.PI) / shapeX.length);

        for (int i = 0; i < shapeX.length; i++) {
            float xProjection = (float) Math.cos(i * radAngleIncrement + radians);
            float yProjection = (float) Math.sin(i * radAngleIncrement + radians);

            shapeX[i] = x + xProjection * normalizedShapeOffsets[i] * radius;
            shapeY[i] = y + yProjection * normalizedShapeOffsets[i] * radius;
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }

    /**
     * Pre-condition:  asteroid
     * Post-condition: Functional asteroid
     * @param asteroid instance
     */
    public void build(Entity asteroid, float startPosX, float startPosY, float velocityRad, float startSpeed) {
        build(asteroid,startPosX,startPosY,velocityRad,startSpeed,baseHitPoint);
    }

    public void build(Entity asteroid, float startPosX, float startPosY, float trajectory, float startSpeed, int hp){
        asteroid.setShapeX(new float[defaultMeshSize]);
        asteroid.setShapeY(new float[defaultMeshSize]);
        asteroid.setColor(defaultColor);
        asteroid.add(new MovingPart(0,0,400,0, startSpeed));
        asteroid.add(new PositionPart(startPosX, startPosY, trajectory));
        LifePart lifePart = new LifePart(baseHitPoint, 1_000_000);
        asteroid.add(lifePart);
        this.setAsteroidRadius(asteroid);
    }


}
