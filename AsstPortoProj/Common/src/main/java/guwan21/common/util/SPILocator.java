package guwan21.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SPILocator {

    private static final Map<Class<?>, ServiceLoader<?>> loadermap = new HashMap<>();

    private SPILocator() {
    }


    public static <T> List<T> locateAll(Class<T> clazz) {
        ServiceLoader<?> loader = loadermap.get(clazz);

        if (loader == null) {
            loader = ServiceLoader.load(clazz);
            loadermap.put(clazz, loader);
        }

        List<T> list = new ArrayList<T>();

        try {
            for (Object instance : loader) {
                try{
                    @SuppressWarnings("unchecked")
                    T duckTyped = (T) instance;
                    list.add(duckTyped);
                }catch (ClassCastException ignored){}
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        }

        return list;
    }
}
