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

    private T objectSource = null;
    private Type type = Type.__INVALID;
    private Category category = Category.__INVALID;
    private Target target = Target.__INVALID;
    private Class<?> targetType = null;
    private Class<?> sourceType = null;

    public Event(){

    }

    public Event(T source, Type type, Category category, Target target) {
        this.target = target;
        this.category = category;
        this.type = type;
        this.objectSource = source;
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
