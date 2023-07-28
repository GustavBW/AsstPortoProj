package guwan21.common.util;

import java.util.*;
import java.util.stream.Collectors;

public class SPILocator {
    private static final Map<Class<?>, ServiceLoader<?>> serviceInstancesMap = new HashMap<>();
    @SuppressWarnings("unchecked")
    public static <T> List<T> locateAll(Class<T> clazz) { //Preserving the loaders
        return (List<T>) serviceInstancesMap.computeIfAbsent(clazz, k -> ServiceLoader.load(clazz)).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}
