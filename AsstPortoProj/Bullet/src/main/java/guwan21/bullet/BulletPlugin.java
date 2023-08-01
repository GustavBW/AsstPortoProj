package guwan21.bullet;

import java.lang.Math;
import java.util.Collection;

import guwan21.common.data.Color;
import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.events.Event;
import guwan21.common.services.IBulletCreator;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;
import guwan21.common.util.SPILocator;

public class BulletPlugin implements IGamePluginService, IBulletCreator {

    IEntityConstructionService bulletConstructor = EntityConstructionServiceRegistry.getFor(Bullet.class);

    @Override
    public void start(GameData data, World world) {

    }

    @Override
    public void stop(GameData data, World world) {
        world.removeEntities(Bullet.class);
    }


    public void checkFiringEvents(GameData data, World world) {
        Collection<Event<?>> events = data.getBroker().getSpecific(
                Entity.class,
                IGamePluginService.class,
                Event.Target.SERVICE,
                Event.Type.INSTANT,
                Event.Category.GAMEPLAY
        );

        for(Event<?> event : events){
            fire((Entity) event.getSource(), world);
        }
    }

    @Override
    public void fire(Entity shotOrigin, World world) {
        Entity bullet = bulletConstructor.create();
        bullet.setParent(shotOrigin);

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

        bulletConstructor.configure(
                bullet,
                new MovingPart(0,0,1000,5,projectileVelocity),
                new PositionPart(x, y, radians),
                new LifePart(1,3)
        );

        System.out.println("pew");

        world.addEntity(bullet);
    }
}
