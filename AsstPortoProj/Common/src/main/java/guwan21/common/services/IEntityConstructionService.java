package guwan21.common.services;

import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.data.entities.Entity;
import guwan21.common.data.entityparts.EntityPart;

public interface IEntityConstructionService {

    /**
     * Creates an entity with default configuration.
     * Any position coordinates and other world-space elements should be provided in normalized form (0-1)
     * pre-condition: An entity instance is requested.
     * post-condition: An entity instance is provided.
     * @return new entity instance
     */
    Entity create();

    /**
     * Adapts the entity to the current state of the game.
     * @param entity to adapt
     * @param data current game state
     * @param world curretn world state
     * pre-condition: An entity which may or may not be adapted to the current game state.
     * post-condition: An adapted entity
     * @return the entity
     */
    Entity adaptTo(Entity entity, GameData data, World world);

    /**
     * Updates the graphical extends of the entity to conform to the sum of its parts.
     * @param entity to update
     * pre-condition: An entity during main processing step
     * post-condition: An entity which graphics have now been updated
     * @return the entity
     */
    Entity updateShape(Entity entity);

    /**
     * Custom build method, configures the current parts on the entity
     * @param entity to build to spec.
     * @param parts replaces current parts on entity
     * @return the entity
     */
    Entity configure(Entity entity, EntityPart... parts);

}
