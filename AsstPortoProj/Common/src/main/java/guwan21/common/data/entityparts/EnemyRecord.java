package guwan21.common.data.entityparts;

import guwan21.common.data.GameData;
import guwan21.common.data.entities.Entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An enemy record is a list of any who can negatively
 * effect the entity with this part.
 * Used by the LifePart to determine whether a hit is valid or not.
 */
public class EnemyRecord implements EntityPart{

    private Set<Class<? extends Entity>> enemies = new HashSet<>();

    public EnemyRecord(){}

    @SafeVarargs
    public EnemyRecord(Class<? extends Entity>... enemies){
        this.enemies = new HashSet<>(List.of(enemies));
    }

    /**
     * If the class of the entity, or any parent entities' class,
     * is in this record, the entity is considered an enemy.
     * @param entity to check
     * @return true if either entity or any parent is contained in the record.
     */
    public boolean contains(Entity entity){
        Entity current = entity;
        while(current != null){
            if(enemies.contains(current.getClass())){
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    @Override
    public void process(GameData gameData, Entity entity) {}
}
