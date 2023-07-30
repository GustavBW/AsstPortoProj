package guwan21.common.events;

import guwan21.common.data.entities.Entity;
import guwan21.common.services.IGamePluginService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Event<T> implements Serializable{

    public enum Category {
        SYSTEM,
        UI,
        GAMEPLAY,
        __INVALID; //For treemap management purposes
    }
    public enum Type {
        /**
         * Removed on consumption
         */
        INSTANT,
        /**
         * Has to be removed manually
         */
        LINGERING,
        __INVALID; //For treemap management purposes

    }
    public enum Target {
        ENTITY,
        SERVICE,
        PLUGIN,
        __INVALID; //For treemap management purposes
    }

    private static final class NotAClass{}
    private static final Object notAnObject = new Object();

    private CompositeHashKey compositeKey;
    private T objectSource = null;
    private Type type = Type.__INVALID;
    private Category category = Category.__INVALID;
    private Target target = Target.__INVALID;
    private Class<?> targetType = NotAClass.class;
    private Class<?> sourceType = NotAClass.class;

    public Event(){
        this.compositeKey = CompositeHashKey.of(this);
    }

    public Event(Object source, Type type, Category category, Target target) {
        this.target = target;
        this.category = category;
        this.type = type;
        this.compositeKey = CompositeHashKey.of(this);
    }

    public Event<T> setType(Type type){
        this.type = type;
        this.compositeKey = CompositeHashKey.of(this);
        return this;
    }
    public Event<T> setCategory(Category category){
        this.category = category;
        this.compositeKey = CompositeHashKey.of(this);
        return this;
    }
    public Event<T> setSource(T source){
        this.objectSource = source;
        this.sourceType = source.getClass();
        this.compositeKey = CompositeHashKey.of(this);
        return this;
    }
    public Event<T> setTarget(Target target){
        this.target = target;
        this.compositeKey = CompositeHashKey.of(this);
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
    public CompositeHashKey getCompositeKey(){
        return compositeKey;
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
