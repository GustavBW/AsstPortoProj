package guwan21.managers;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    public static void forAnyOfEither(AnnotationConfigApplicationContext context, LinkedHashMap<Class<?>, VoidFunction<?>> functions){
        for(Class<?> clazz : functions.keySet()){
            functions.computeIfAbsent(clazz, k -> (t) -> {});
            try{
                @SuppressWarnings("rawtypes")
                VoidFunction func = functions.get(clazz);
                Object bean = context.getBean(clazz);
                func.run(bean);
                System.out.println("On update ran forAnyOfEither for class: " + clazz.toString());
            }catch (ClassCastException | IllegalArgumentException | NullPointerException ignored){
                System.out.println("Unable to run provided / empty function for class: " + clazz.toString());
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
