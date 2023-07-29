package guwan21.asteroid;

import guwan21.common.data.Color;
import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.EntityPart;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IEntityConstructionService;

public class AsteroidConstructionService implements IEntityConstructionService {

    private final int defaultMeshSize = 8, baseHitPoint = 3;
    private final Color defaultColor = new Color(1,1,1,1);

    /**
     * Pre-condition: Asteroid entity<br />
     * Post-condition: Asteroid entity with correct radius
     *
     * @param asteroid Asteroid entity to have its radius updated
     */
    private void setAsteroidRadius(Entity asteroid) {
        asteroid.setRadius(asteroid.getPart(LifePart.class).getLife() * 15);
    }


    /**
     * Create initial asteroid
     * Pre-condition: An asteroid is to be created;
     * Post-condition: A new asteroid instance
     * @return new instance
     */
    @Override
    public Entity create() {

        Entity asteroid = new Asteroid();

        asteroid.setShapeX(new float[defaultMeshSize]);
        asteroid.setShapeY(new float[defaultMeshSize]);
        asteroid.setColor(defaultColor);
        asteroid.add(new LifePart(baseHitPoint, 1_000_000));
        asteroid.add(new MovingPart(0,0,400,0, (float) (Math.random() * 50f) + 25f));
        asteroid.add(new PositionPart((float) Math.random(), (float) Math.random(), (float) (Math.random() * (2 * Math.PI))));

        this.setAsteroidRadius(asteroid);

        return asteroid;
    }

    @Override
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
    @Override
    public Entity updateShape(Entity entity) {
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
        setAsteroidRadius(entity);
        return entity;
    }


    @Override
    public Entity configure(Entity entity, EntityPart... parts){
        for(EntityPart part : parts) entity.add(part);
        return entity;
    }

}
