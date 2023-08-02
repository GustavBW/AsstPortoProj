package guwan21.core.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Static Spring Beans utility.
 * Allows for easy lookups, scans, and functional patterns.
 * @author GustavBW
 */
public class SpringBeansManager {

    @FunctionalInterface
    public interface VoidFunction<T>{
        void run(T obj);
    }

    /**
     * K = package name, V = context
     */
    private static final Map<String,AnnotationConfigApplicationContext> contextCache = new ConcurrentHashMap<>();

    /**
     * Retrieves a context containing beans of said class, <br>
     * then runs the function with any bean available. Possibly null.<br>
     * @param clazz class to scan for<br>
     * @param function function to run for bean <br>
     * @param <T> type param
     */
    public static <T> void forAnyOf(Class<T> clazz, VoidFunction<T> function){
        forAnyOf(clazz, clazz.getPackageName(), function);
    }

    /**
     * Retrieves a context containing beans of said class, <br>
     * then runs the function with any bean available. Possibly null.<br>
     * @param clazz class to scan for<br>
     * @param function function to run for bean <br>
     * @param pack name of the package in which the class declaration should be</br>
     * @param <T> type param
     */
    public static <T> void forAnyOf(Class<T> clazz, String pack, VoidFunction<T> function){
        function.run(getContext(pack).getBean(clazz));
    }

    /**
     * Scans everything in that package and returns the context</br>
     * @param pack package name
     * @return AnnotationConfigApplicationContext
     */
    public static AnnotationConfigApplicationContext getContextFor(String pack){
        return getContext(pack);
    }

    /**
     * Creates a new context and runs the provided function - empty func if null - in the order of the classes in keySet
     * @param functions Function map, do provide a function taking in an instance of any type in the class list
     */
    public static void forAnyOfEither(String pack, LinkedHashMap<Class<?>, VoidFunction<?>> functions){
        forAnyOfEither(getContext(pack),functions);
    }

    /**
     * For any of either class in the key set of the map (preserves order), try and execute the function provided as its value (empty if null)
     * with a bean provided by the context. May throw a BeansException.
     * @param context Spring AnnotationConfigApplicationContext
     * @param functions Map of classes and functions
     */
    @SuppressWarnings("unchecked")
    public static void forAnyOfEither(AnnotationConfigApplicationContext context, LinkedHashMap<Class<?>, VoidFunction<?>> functions){
        for(Class<?> clazz : functions.keySet()){
            try{
                @SuppressWarnings("rawtypes")
                VoidFunction func = functions.getOrDefault(clazz, k -> {}); //Empty function if null
                func.run(context.getBean(clazz));
            }catch (ClassCastException | IllegalArgumentException ignored){
                System.out.println("Unable to run provided / empty function for class: " + clazz.toString() + " during Game update cycle.");
            }
        }
    }

    public static <T> T getBean(Class<T> clazz){
        return getBean(clazz.getPackageName(), clazz);
    }

    public static <T> T getBean(String pack, Class<T> clazz){
        return getContext(pack).getBean(clazz);
    }

    private static AnnotationConfigApplicationContext getContext(String pack){
        AnnotationConfigApplicationContext context = contextCache.get(pack);
        if(context != null){
            return context;
        }
        context = new AnnotationConfigApplicationContext();
        context.scan(pack);
        context.refresh();
        contextCache.put(pack, context);
        return context;
    }

}
