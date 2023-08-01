package guwan21.bullet;

import java.lang.Math;
import java.util.Collection;

import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.events.Event;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;

public class BulletPlugin implements IGamePluginService{

    IEntityConstructionService bulletConstructor = EntityConstructionServiceRegistry.getFor(Bullet.class);

    @Override
    public void start(GameData data, World world) {
        //TODO: Subscribe to broker.
    }

    @Override
    public void stop(GameData data, World world) {
        world.removeEntities(Bullet.class);
    }


    public void checkFiringEvents(GameData data, World world) {
        Collection<Event<?>> events = data.getBroker().querySpecific(
                Player.class,
                IGamePluginService.class,
                Event.Target.SERVICE,
                Event.Type.INSTANT,
                Event.Category.GAMEPLAY
        );

        for(Event<?> event : events){
            fire((Entity) event.getSource(), world);
        }

        events.forEach(event -> { //Uneccessary on automated consumptions when subscriptions are introduced
            if(event.getType() == Event.Type.INSTANT){
                data.getBroker().removeEvent(event);
            }
        });
    }

    public void fire(Entity shotOrigin, World world) {
        Entity bullet = bulletConstructor.create();
        bullet.setParent(shotOrigin);

        bullet.setColor(shotOrigin.getColor());

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

        System.out.println("Bullet Plugin \"pew\" by " + shotOrigin.getClass());

        world.addEntity(bullet);
    }
}
