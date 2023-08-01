package guwan21.common.events;

import java.io.Serializable;

public class Event<T> implements Serializable{

    /**
     * What category an Event is in.
     * Default: System.
     */
    public enum Category {
        SYSTEM,
        UI,
        GAMEPLAY;
    }

    /**
     * What type of Event it is.
     * Default: Instant.
     */
    public enum Type {
        /**
         * Removed on consumption
         */
        INSTANT,
        /**
         * Has to be removed manually
         */
        LINGERING;

    }

    /**
     * What is the purpose of the target of the Event.
     * Default: Service.
     */
    public enum Target {
        ENTITY,
        SERVICE,
        PLUGIN;
    }

    private T objectSource = null;
    private Type type = Type.INSTANT;
    private Category category = Category.SYSTEM;
    private Target target = Target.SERVICE;
    private Class<?> targetType = null;
    private Class<?> sourceType = null;

    public Event(){

    }

    public Event(T source, Type type, Category category, Target target) {
        this.target = target;
        this.category = category;
        this.type = type;
        this.objectSource = source;
        this.sourceType = source.getClass();
    }

    public Event<T> setType(Type type){
        this.type = type;
        return this;
    }
    public Event<T> setCategory(Category category){
        this.category = category;
        return this;
    }
    public Event<T> setSource(T source){
        this.objectSource = source;
        this.sourceType = source.getClass();
        return this;
    }
    public Event<T> setTarget(Target target){
        this.target = target;
        return this;
    }
    public Event<T> setTargetType(Class<?> clazz){
        this.targetType = clazz;
        return this;
    }

    public T getSource(){
        return objectSource;
    }
    public Class<?> getSourceType(){
        return sourceType;
    }
    public Category getCategory(){
        return category;
    }
    public Type getType(){
        return type;
    }
    public Target getTarget(){
        return target;
    }
    public Class<?> getTargetType(){
        return targetType;
    }
}
