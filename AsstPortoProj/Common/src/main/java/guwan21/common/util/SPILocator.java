package guwan21.common.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Revised ServiceLoader utility.
 * @author GustavBW
 */
public class SPILocator {
    private static final Map<Class<?>, List<?>> serviceInstancesMap = new HashMap<>();
    private static final Map<Class<?>, List<ServiceLoader.Provider<?>>> servicesProvidersMap = new HashMap<>();
    /**
     * Loads any service providers of said type, caches and returns the instances provided.
     * May throw a ClassCastException on service configuration error.
     * @param clazz Type implementations to look for
     * @return instances of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> locateBeans(Class<T> clazz) {
        return (List<T>) serviceInstancesMap.computeIfAbsent(clazz, k -> ServiceLoader.load(clazz).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList()));
    }

    /**
     * Loads any service providers of said type, caches and returns the providers.
     * Duly note that invoking the providers returns a new bean instance.
     * May throw a ClassCastException on service configuration error.
     * @param clazz Type of provider to look for
     * @return providers for said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<ServiceLoader.Provider<T>> locateProviders(Class<T> clazz){
        return (List<ServiceLoader.Provider<T>>) serviceInstancesMap.computeIfAbsent(clazz, k -> ServiceLoader.load(clazz).stream().collect(Collectors.toList()));
    }
}
