package guwan21.asteroid;

import guwan21.common.data.Color;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;

public class AsteroidConstructor {

    private final int defaultMeshSize = 8, baseHitPoint = 3;
    private final Color defaultColor = new Color(1,1,1,1);

    /**
     * Adjust asteroid radius.
     * <br />
     * Pre-condition: Asteroid entity<br />
     * Post-condition: Asteroid entity with correct radius
     *
     * @param asteroid Asteroid entity to have its radius updated
     */
    public void setAsteroidRadius(Entity asteroid) {
        float radius = switch (((LifePart) asteroid.getPart(LifePart.class)).getLife()) {
            case 1 -> 10;
            case 2 -> 15;
            default -> 25;
        };
        asteroid.setRadius(radius);
    }

    /**
     * Pre-condition:  asteroid
     * Post-condition: Functional asteroid
     * @param asteroid instance
     */
    public void build(Entity asteroid, float startPosX, float startPosY, float velocityRad, float startSpeed) {
        asteroid.setShapeX(new float[defaultMeshSize]);
        asteroid.setShapeY(new float[defaultMeshSize]);
        asteroid.setColor(defaultColor);
        asteroid.add(new MovingPart(0,0,400,0, startSpeed));
        asteroid.add(new PositionPart(startPosX, startPosY, velocityRad));
        LifePart lifePart = new LifePart(baseHitPoint, 0);
        asteroid.add(lifePart);
        this.setAsteroidRadius(asteroid);
    }


    /**
     * Create initial asteroid
     * Pre-condition: An asteroid is to be created;
     * Post-condition: A new asteroid instance
     * @param gameBoundsX viewport width
     * @param gameBoundsY viewport height
     * @return new instance
     */
    public Entity create(float gameBoundsX, float gameBoundsY) {

        Entity asteroid = new Asteroid();

        this.build(
                asteroid,
                (float) Math.random() * gameBoundsX,
                (float) Math.random() * gameBoundsY,
                (float) (Math.random() * (2 * Math.PI)),
                (float) (Math.random() * 50f) + 25f
        );

        return asteroid;
    }


}
