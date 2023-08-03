package guwan21.enemy;

import java.lang.Math;
import java.util.List;
import java.util.function.Function;

import guwan21.common.data.entities.Enemy;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Player;
import guwan21.common.data.entityparts.LifePart;
import guwan21.common.data.entityparts.MovingPart;
import guwan21.common.data.entityparts.WeaponPart;
import guwan21.common.events.Event;
import guwan21.common.events.EventQueryParameters;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;

public class EnemyProcessingService implements IEntityProcessingService {

    private final EnemyConstructionService constructor = new EnemyConstructionService();
    private final EventQueryParameters playerIsDeadQuery = new EventQueryParameters(
            Player.class, Event.ANY_CLASS, Event.Target.SERVICE, Event.Type.INSTANT, Event.Category.GAMEPLAY, "Player Death"
    );
    @FunctionalInterface
    private interface OnProcessRun{
        void run(GameData data, World world);
    }
    private final List<OnProcessRun> onProcessRun = List.of(
            this::subscribeToPlayerDeath, //First process pass just subscribes
            this::mainProcessPass, //Then comes the main runtime for the service
            this::whenPlayerIsDead //And when the player dies the enemies will.. yeah you'll see.
    );
    private int functionPointer = 0;

    @Override
    public void process(GameData data, World world) {
        //Branchless pattern. Allows for multiple different conditional branches with little to no overhead
        onProcessRun.get(functionPointer).run(data,world);
    }

    private void subscribeToPlayerDeath(GameData data, World world){
        data.getBroker().subscribe(this::onPlayerDied, playerIsDeadQuery);
        functionPointer++;
    }
    private boolean onPlayerDied(Event<?> event){
        functionPointer++;
        return true;
    }


    private void mainProcessPass(GameData data, World world){
        for (Entity enemy : world.getEntities(Enemy.class)) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            WeaponPart weaponPart = enemy.getPart(WeaponPart.class);

            enemy.getParts().forEach(ep -> ep.process(data,enemy));
            if (enemy.getPart(LifePart.class).isDead()) {
                world.removeEntity(enemy);
                continue;
            }

            constructor.updateShape(enemy);

            if(Math.floor(data.getMsFromGameStart() % 2) == 0){
                final int rand = (int) (Math.random() * 3);
                movingPart.setUp(false);
                movingPart.setRight(false);
                movingPart.setLeft(false);
                weaponPart.setFiring(false);
                switch (rand){
                    case 0 -> movingPart.setLeft(true);
                    case 1 -> movingPart.setRight(true);
                    case 2 -> movingPart.setUp(true);
                }
                continue;
            }

            weaponPart.setFiring(true);
            if (weaponPart.isFiring()) {
                data.getBroker().publish(
                        new Event<>(
                                enemy,
                                Event.Type.INSTANT,
                                Event.Category.GAMEPLAY,
                                Event.Target.SERVICE
                        ).setTargetType(IGamePluginService.class)
                                .setName("Enemy Firing")
                );
            }

        }
    }

    private void whenPlayerIsDead(GameData data, World world){
        for (Entity enemy : world.getEntities(Enemy.class)) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            WeaponPart weaponPart = enemy.getPart(WeaponPart.class);

            enemy.getParts().forEach(ep -> ep.process(data,enemy));
            if (enemy.getPart(LifePart.class).isDead()) {
                world.removeEntity(enemy);
                continue;
            }

            constructor.updateShape(enemy);

            movingPart.setLeft(true);

            weaponPart.setFiring(true);
            if (weaponPart.isFiring()) {
                data.getBroker().publish(
                        new Event<>(
                                enemy,
                                Event.Type.INSTANT,
                                Event.Category.GAMEPLAY,
                                Event.Target.SERVICE
                        ).setTargetType(IGamePluginService.class)
                                .setName("Enemy Firing")
                );
            }

        }
    }





}












