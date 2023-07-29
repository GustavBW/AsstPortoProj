package guwan21.common.util;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Asteroid;
import guwan21.common.data.entities.Bullet;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.entityparts.EntityPart;
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
        public Entity configure(Entity entity, EntityPart... parts) {
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
        public Entity configure(Entity entity, EntityPart... parts) {
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
        public Entity configure(Entity entity, EntityPart... parts) {
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

        //Overwrite the map
        EntityConstructionServiceRegistry registry = new EntityConstructionServiceRegistry(constructionServices);
        //If I request a construction service for the bullet class, it should return the bulletConstructor instance
        assertEquals(bulletConstructor,EntityConstructionServiceRegistry.getFor(Bullet.class));
        assertEquals(asteroidConstructor,EntityConstructionServiceRegistry.getFor(Asteroid.class));

    }
}