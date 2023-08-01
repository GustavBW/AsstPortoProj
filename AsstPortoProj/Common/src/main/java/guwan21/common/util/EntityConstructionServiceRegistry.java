package guwan21.common.util;

import guwan21.common.data.entities.Entity;
import guwan21.common.services.IEntityConstructionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service locator utility for IEntityConstructionService implementations
 * @author GustavBW
 */
public class EntityConstructionServiceRegistry {

    private static Map<Class<?>, IEntityConstructionService> constructionServices = new ConcurrentHashMap<>();

    /**
     * Searches for a IEntityConstructionService producing the right entity subtype, instances it, caches the instance and returns it.
     * If no service is available, it will return null.
     * @param entityClazz The entity subtype the service has to produce.
     * @return The construction service for said entity subtype.
     */
    public static IEntityConstructionService getFor(Class<? extends Entity> entityClazz) {
        return constructionServices.computeIfAbsent(entityClazz, value ->
            SPILocator.getBeans(IEntityConstructionService.class)
                    .stream()
                    .filter(service -> entityClazz.isInstance(service.create()))
                    .findFirst()
                    .orElse(null)
        );
    }


    //Package private, as this is for testing purposes
    static void overwriteMap(Map<Class<?>, IEntityConstructionService> overwrite){
        constructionServices = overwrite;
    }

}
