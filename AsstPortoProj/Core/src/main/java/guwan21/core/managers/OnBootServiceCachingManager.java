package guwan21.core.managers;

import guwan21.common.data.entities.Entity;
import guwan21.common.util.EntityConstructionServiceRegistry;
import guwan21.common.util.SPILocator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Scans for available resource at a jar-artifact / directory level.
 */
@Component
public class OnBootServiceCachingManager implements IBootLoader{
    private static class NOT_A_CLASS{}
    @Override
    public void run(String[] args) {

        List<URL> contentsOfCommonServices = scanPackage("guwan21.common.services");
        List<URL> contentsOfCommonFactories = scanPackage("guwan21.common.factories");
        List<URL> contentsOfCommonDataEntities = scanPackage("guwan21.common.data.entities");

        List<Class<?>> unpackedServiceTypes = unpackResources(contentsOfCommonServices,"guwan21.common.services");
        List<Class<?>> unpackedFactoryTypes = unpackResources(contentsOfCommonFactories, "guwan21.common.factories");
        List<Class<?>> unpackedUnknownEntityTypes = unpackResources(contentsOfCommonDataEntities, "guwan21.common.data.entities");

        unpackedServiceTypes.addAll(unpackedFactoryTypes);
        unpackedServiceTypes = unpackedServiceTypes.stream().filter(clazz -> clazz != NOT_A_CLASS.class).toList();
        List<Class<? extends Entity>> unpackedCheckedEntityTypes = protectedCast(
            unpackedUnknownEntityTypes.stream()
                .filter(clazz -> clazz != NOT_A_CLASS.class)
                .filter(clazz -> !Entity.class.equals(clazz) && Entity.class.isAssignableFrom(clazz))
                .toList()
        );

        System.out.println("[OnBootInitializationManager]: Caching Entity Construction Services");
        for(Class<? extends Entity> clazz : unpackedCheckedEntityTypes){
            EntityConstructionServiceRegistry.getFor(clazz);
        }
        System.out.println("[OnBootInitializationManager]: Caching Any Other Services");
        for(Class<?> clazz : unpackedServiceTypes){
            SPILocator.locateBeans(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Class<? extends Entity>> protectedCast(List<Class<?>> list){
        try{
            return (List<Class<? extends Entity>>) list;
        }catch(ClassCastException e){
            return Collections.emptyList();
        }
    }

    /**
     * Scans a package for ressources.
     * @param packageName package to scan
     * @return list of urls in said package
     */
    private List<URL> scanPackage(String packageName) {
        List<URL> asList = new ArrayList<>();
        try {
            // Get the ClassLoader of the current module
            ClassLoader classLoader = getClass().getClassLoader();

            // Use the ClassLoader to get all the resources (class files) in the package
            classLoader.getResources(packageName.replace('.', '/'))
                    .asIterator().forEachRemaining(asList::add);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return asList;
    }

    /**
     * Unpacks each URL, checking if they're either jar artefacts or directories
     * and traverses accordingly
     * @param resources url list
     * @param packageName for classes to be in
     * @return list of unknown classes
     */
    private List<Class<?>> unpackResources(List<URL> resources, String packageName){
        List<Class<?>> unpacked = new ArrayList<>();

        try{
            for(URL resource : resources) {
                if (resource.getProtocol().equals("file")) {
                    // Handle classes in a directory
                    File directory = new File(resource.toURI());
                    unpacked.addAll(scanClassesInDirectory(directory, packageName));
                } else if (resource.getProtocol().equals("jar")) {
                    // Handle classes in a JAR file
                    JarURLConnection jarConnection = (JarURLConnection) resource.openConnection();
                    JarFile jarFile = jarConnection.getJarFile();
                    unpacked.addAll(scanClassesInJar(jarFile, packageName));
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return unpacked;
    }

    /**
     * Uses a depth-first recursive approach to retrieve all files,
     * and for each get the classOf() value.
     * @param directory to scan
     * @param packageName for classes to be in
     * @return list of unknown classes
     */
    private List<Class<?>> scanClassesInDirectory(File directory, String packageName) {
        if(!directory.isDirectory()) return List.of(classOf(directory.getName(),packageName));

        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
            .flatMap(fileOrDir -> {
                if (fileOrDir.isDirectory()) { //Recursive, depth-first unraveling of directories
                    return scanClassesInDirectory(fileOrDir, packageName + "." + fileOrDir.getName()).stream();
                } else {
                    return Stream.of(classOf(fileOrDir.getName(), packageName));
                }
            })
            .collect(Collectors.toList());
    }

    /**
     * Gets the class of that name in said package.
     * If no such class, returns NOT_A_CLASS.class
     * @param name of class
     * @param packageName of package
     * @return unknown class
     */
    private Class<?> classOf(String name, String packageName){
        try{
            //Reflection invocation
            return Class.forName(packageName + "." + name.substring(0, name.length() - 6));
        }catch (ClassNotFoundException ignored){
            return NOT_A_CLASS.class;
        }
    }


    /**
     * For each entry in the jarfile, if its name happens to start with the packageName
     * and end with .class, add its OnBootInitializationManager.classOf() value to the list returned.
     * @param jarFile to scan
     * @param packageName of expected classes
     * @return List of unknown classes
     */
    private List<Class<?>> scanClassesInJar(JarFile jarFile, String packageName) {
        List<Class<?>> toReturn = new ArrayList<>();

        jarFile.entries().asIterator().forEachRemaining(
                jarEntry -> {
                    String name = jarEntry.getName();
                    if (name.startsWith(packageName) && name.endsWith(".class")) {
                        String className = name.substring(0, name.length() - 6).replace('/', '.');
                        toReturn.add(classOf(className,packageName));
                    }
                }
        );

        return toReturn;
    }

}
