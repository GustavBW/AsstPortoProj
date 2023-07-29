package guwan21.common.util;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.services.IEntityConstructionService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EntityConstructionServiceRegistryTest {

    private static class BulletConstructorTestClass implements IEntityConstructionService{

        @Override
        public Entity create() {
            return new Bullet();
        }

        @Override
        public Entity adaptTo(Entity entity, GameData data, World world) {
            return null;
        }

        @Override
        public Entity updateShape(Entity entity) {
            return null;
        }

        @Override
        public Entity build(Entity entity, float startPosX, float startPosY, float direction, float startSpeed, int hp) {
            return null;
        }
    }
    private static class AsteroidConstructorTestClass implements IEntityConstructionService{

        @Override
        public Entity create() {
            return new Asteroid();
        }

        @Override
        public Entity adaptTo(Entity entity, GameData data, World world) {
            return null;
        }

        @Override
        public Entity updateShape(Entity entity) {
            return null;
        }

        @Override
        public Entity build(Entity entity, float startPosX, float startPosY, float direction, float startSpeed, int hp) {
            return null;
        }
    }
    private static class DontReturnThisConstructorTestClass implements IEntityConstructionService{
        @Override
        public Entity create() {
            return new Entity();
        }

        @Override
        public Entity adaptTo(Entity entity, GameData data, World world) {
            return null;
        }

        @Override
        public Entity updateShape(Entity entity) {
            return null;
        }

        @Override
        public Entity build(Entity entity, float startPosX, float startPosY, float direction, float startSpeed, int hp) {
            return null;
        }
    }

    @Test
    void getFor() {
        Map<Class<?>, IEntityConstructionService> constructionServices = new HashMap<>();
        AsteroidConstructorTestClass asteroidConstructor = new AsteroidConstructorTestClass();
        BulletConstructorTestClass bulletConstructor = new BulletConstructorTestClass();
        DontReturnThisConstructorTestClass notThis = new DontReturnThisConstructorTestClass();

        constructionServices.put(Entity.class, notThis);
        constructionServices.put(Bullet.class, bulletConstructor);
        constructionServices.put(Asteroid.class, asteroidConstructor);

        EntityConstructionServiceRegistry registry = new EntityConstructionServiceRegistry(constructionServices);

    }
}