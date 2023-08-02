package guwan21.bullet;

import java.lang.Math;
import java.util.function.Function;

import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.PositionPart;
import guwan21.common.events.Event;
import guwan21.common.events.EventQueryParameters;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.util.EntityConstructionServiceRegistry;

public class BulletPlugin implements IGamePluginService{

    private final IEntityConstructionService bulletConstructor = EntityConstructionServiceRegistry.getFor(Bullet.class);
    private final EventQueryParameters firingEventsQueryPlayer = new EventQueryParameters(
            Player.class,
            IGamePluginService.class,
            Event.Target.SERVICE,
            Event.Type.INSTANT,
            Event.Category.GAMEPLAY
    );
    private final EventQueryParameters firingEventsQueryEnemy = new EventQueryParameters(
            Enemy.class,
            IGamePluginService.class,
            Event.Target.SERVICE,
            Event.Type.INSTANT,
            Event.Category.GAMEPLAY
    );
    private Function<Event<?>,Boolean> subscription1 = null;
    private Function<Event<?>,Boolean> subscription2 = null;

    @Override
    public void start(GameData data, World world) {
        subscription1 = data.getBroker().subscribe(event -> fireOnEvent(event, world),firingEventsQueryPlayer);
        subscription2 = data.getBroker().subscribe(event -> fireOnEvent(event, world),firingEventsQueryEnemy);
    }

    @Override
    public void stop(GameData data, World world) {
        data.getBroker().unsubscribe(subscription1,firingEventsQueryPlayer);
        data.getBroker().unsubscribe(subscription2,firingEventsQueryEnemy);
        world.removeEntities(Bullet.class);
    }

    private boolean fireOnEvent(Event<?> event, World world){
        fire((Entity) event.getSource(),world);
        return true;
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

        world.addEntity(bullet);
    }
}
