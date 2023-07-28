package guwan21.bullet;

import java.lang.Math;
import guwan21.common.data.Color;
import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService, IBulletCreator {

    @Override
    public void start(GameData data, World world) {

    }

    /**
     * Create bullet entity with default parameters based on shooter
     * <br />
     * Pre-condition: New bullet entity has to be created for the game from a shooter <br />
     * Post-condition: Bullet entity, that has parameters, such that it is shot from shooter
     *
     * @return Bullet entity with initial data from shooter
     */
    private Entity create(Entity shotOrigin) {
        Entity bullet = new Bullet();

        bullet.setRadius(1);
        PositionPart soPosition = shotOrigin.getPart(PositionPart.class);
        final float radians = soPosition.getRadians();
        final float radius = shotOrigin.getRadius();

        float dx = (float) (Math.cos(radians) * radius);
        float dy = (float) (Math.sin(radians) * radius);

        MovingPart soMovePart = shotOrigin.getPart(MovingPart.class);
        float bx = (float) Math.cos(radians) * radius;
        float x = bx + soPosition.getX() + dx;
        float by = (float) Math.sin(radians) * radius;
        float y = by + soPosition.getY() + dy;
        float projectileVelocity = 350 + (soMovePart.getSpeed());

        bullet.setShapeX(new float[4]);
        bullet.setShapeY(new float[4]);
        bullet.setColor(new Color(1,1,1,1));
        bullet.add(new MovingPart(0,0,1000,5, projectileVelocity));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1, 3));

        return bullet;
    }

    @Override
    public void stop(GameData data, World world) {
        world.removeEntities(Bullet.class);
    }

    @Override
    public void fire(Entity shotOrigin, World world) {
        Entity bullet = this.create(shotOrigin);
        world.addEntity(bullet);
    }
}
