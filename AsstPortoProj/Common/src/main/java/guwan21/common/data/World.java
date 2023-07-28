package guwan21.common.data;

import guwan21.common.data.entities.Entity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jcs
 */
public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        String id = entity.getID();
        entityMap.put(id, entity);
        return id;
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }
    
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> void removeEntities(Class<E> type){
        for(Map.Entry<String,Entity> entry : entityMap.entrySet()){
            if(type.equals(entry.getValue().getClass())){
                entityMap.remove(entry.getKey());
            }
        }
    }

    public List<String> addEntities(Entity... entities){
        List<String> toReturn = new ArrayList<>();
        for(Entity e : entities){
            toReturn.add(addEntity(e));
        }
        return toReturn;
    }

    @SafeVarargs
    public final <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public List<Entity> getEntities(String className){
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            String name = e.getClass().getName();
            String nameWithoutPackage = name.substring(name.lastIndexOf(".") + 1);
            if (className.equals(nameWithoutPackage)) {
                r.add(e);
            }

        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

}
