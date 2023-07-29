package guwan21.common.util;

import guwan21.common.data.entities.Entity;
import guwan21.common.services.IEntityConstructionService;
import guwan21.common.util.SPILocator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service locator utility for IEntityConstructionService implementations
 * @author GustavBW
 */
public class EntityConstructionServiceRegistry {

    private static Map<Class<?>, IEntityConstructionService> constructionServices = new HashMap<>();

    public EntityConstructionServiceRegistry(){}
    public EntityConstructionServiceRegistry(Map<Class<?>, IEntityConstructionService> overwrite){
        constructionServices = overwrite;
    }

    /**
     * Searches for a IEntityConstructionService producing the right entity subtype, instances it, caches the instance and returns it.
     * If no service is available, it will return null.
     * @param entityClazz The entity subtype the service has to produce.
     * @return The construction service for said entity subtype.
     */
    public static <T extends Entity> IEntityConstructionService getFor(Class<T> entityClazz) {
        return constructionServices.computeIfAbsent(entityClazz, value -> {
            Optional<IEntityConstructionService> optionalService = SPILocator.locateBeans(IEntityConstructionService.class)
                    .stream()
                    .filter(service -> entityClazz.isInstance(service.create()))
                    .findFirst();

            return optionalService.orElse(null);
        });
    }

}