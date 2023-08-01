package guwan21.common.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.ServiceLoader.Provider;
import java.util.ServiceLoader;
import java.util.stream.Stream;

/**
 * Revised ServiceLoader utility.
 * @author GustavBW
 */
public class SPILocator{

    private static final Map<Class<?>, List<?>> serviceInstancesMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, List<Provider<?>>> servicesProvidersMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, ServiceLoader<?>> servicesLoaderMap = new ConcurrentHashMap<>();

    /**
     * Loads any service providers of said type, caches and returns the instances provided.
     * May throw a ClassCastException on service configuration error.
     * @param clazz Type implementations to look for
     * @return instances of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getBeans(Class<T> clazz) {
        return (List<T>) serviceInstancesMap.computeIfAbsent(clazz, k -> getProvidersOf(clazz)
                .stream()
                .map(Provider::get)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new))
        );
    }

    /**
     * Loads any service providers of said type, caches and returns the providers.
     * Duly note that invoking the providers returns a new instance.
     * May throw a ClassCastException on service configuration error.
     * @param clazz Type of provider to look for
     * @return providers of said type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Provider<T>> getProvidersOf(Class<T> clazz){
        return (List<Provider<T>>) (List<?>) servicesProvidersMap.computeIfAbsent(
                clazz, k -> getLoader(clazz)
                        .stream()
                        .collect(Collectors.toCollection(CopyOnWriteArrayList::new)));
    }

    /**
     * Retrieves a ServiceLoader of said type.
     * @param clazz Type of service to load
     * @return a ServiceLoader for that type
     */
    @SuppressWarnings("unchecked")
    public static <T> ServiceLoader<T> getLoaderFor(Class<T> clazz){
        return (ServiceLoader<T>) getLoader(clazz);
    }

    private static ServiceLoader<?> getLoader(Class<?> clazz){
        return servicesLoaderMap.computeIfAbsent(clazz, k -> ServiceLoader.load(clazz));
    }
}
