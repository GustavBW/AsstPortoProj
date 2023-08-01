package guwan21.core.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public static <T> void forAnyOf(Class<T> clazz, VoidFunction<T> function){
        forAnyOf(clazz, clazz.getPackageName(), function);
    }

    public static <T> void forAnyOf(Class<T> clazz, String pack, VoidFunction<T> function){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(pack);
        context.refresh();
        function.run(context.getBean(clazz));
    }

    public static AnnotationConfigApplicationContext getContextFor(String pack){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(pack);
        context.refresh();
        return context;
    }

    /**
     * Creates a new context and runs the provided function - empty func if null - in the order of the classes in keySet
     * @param functions Function map, do provide a function taking in an instance of any type in the class list
     */
    public static void forAnyOfEither(String pack, LinkedHashMap<Class<?>, VoidFunction<?>> functions){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(pack);
        context.refresh();
        forAnyOfEither(context,functions);
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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(pack);
        context.refresh();
        return context.getBean(clazz);
    }

}
