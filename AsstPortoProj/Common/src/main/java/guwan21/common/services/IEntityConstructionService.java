package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.entityparts.EntityPart;

public interface IEntityConstructionService {

    /**
     * Creates an entity with default configuration.<br>
     * Any position coordinates and other world-space elements should be provided in normalized form (0-1)<br>
     * pre-condition: An entity instance is requested.<br>
     * post-condition: An entity instance is provided.<br>
     * @return new entity instance
     */
    Entity create();

    /**
     * Adapts the entity to the current state of the game.<br>
     * @param entity to adapt<br>
     * @param data current game state<br>
     * @param world current world state<br>
     * pre-condition: An entity which may or may not be adapted to the current game state.<br>
     * post-condition: An adapted entity<br>
     * @return the entity
     */
    Entity adaptTo(Entity entity, GameData data, World world);

    /**
     * Updates the graphical extends of the entity to conform to the sum of its parts.<br>
     * @param entity to update<br>
     * pre-condition: An entity is during its main processing step<br>
     * post-condition: An entity which graphics have now been updated as part of that step<br>
     * @return the entity
     */
    Entity updateShape(Entity entity);

    /**
     * Custom build method, configures the current parts on the entity<br>
     * @param entity to build to spec.<br>
     * @param parts replaces current parts on entity<br>
     * pre-condition: An entity with some amount of configuration<br>
     * post-condition: An entity with any amount of configuration<br>
     * @return the entity
     */
    Entity configure(Entity entity, EntityPart... parts);

}
