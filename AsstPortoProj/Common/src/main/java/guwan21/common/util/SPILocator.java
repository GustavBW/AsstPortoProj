package guwan21.common.util;

import java.util.*;

public class SPILocator {

    private static final Map<Class<?>, List<?>> serviceInstancesMap = new HashMap<>();

    private record ClassBasedMapEntry<T>(Class<T> classKey, Collection<T> classInstanceValues){}

    private SPILocator() {
    }
    @SuppressWarnings("unchecked")
    public static <T> List<T> locateAll(Class<T> clazz) {
        if (serviceInstancesMap.get(clazz) == null) {
            ServiceLoader<T> loader = ServiceLoader.load(clazz);

            List<T> asList = new ArrayList<>();
            loader.forEach(asList::add);

            serviceInstancesMap.put(clazz, asList);
            return asList;
        }
        return (List<T>) serviceInstancesMap.get(clazz);
    }
}
