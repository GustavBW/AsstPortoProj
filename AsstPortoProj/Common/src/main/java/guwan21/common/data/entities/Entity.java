package guwan21.common.data.entities;

import guwan21.common.data.Color;
import guwan21.common.data.entityparts.EntityPart;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private Color color;
    private final Map<Class<? extends EntityPart>, EntityPart> parts;
    private Entity parent = null;
    
    public Entity() {
        parts = new ConcurrentHashMap<>();
        color = new Color(1,1,1,1);
    }
    
    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }
    
    public <T> void remove(Class<T> partClass) {
        parts.remove(partClass);
    }

    @SuppressWarnings("unchecked")
    public <E extends EntityPart> E getPart(Class<E> partClass) {
        return (E) parts.get(partClass);
    }

    public Collection<EntityPart> getParts(){
        return parts.values();
    }

    public void setRadius(float r){
        this.radius = r;
    }
    
    public float getRadius(){
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setParent(Entity parent){
        if(parent != this){
            this.parent = parent;
        }
    }
    public Entity getParent(){
        return parent;
    }
}
