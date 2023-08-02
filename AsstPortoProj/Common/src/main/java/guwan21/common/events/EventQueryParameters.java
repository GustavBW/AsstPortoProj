package guwan21.common.events;

//Restructured from record as to provide null-safety required for the current implementation of IEventBroker
public class EventQueryParameters {

    private Class<?> emittorClass;
    private Class<?> targetClass;
    private Event.Target target;
    private Event.Type type;
    private Event.Category category;
    private String name;

    /**
     * @param emittorClass The class of the object that presumably emitted the event
     * @param targetClass What class the event targets
     * @param target What is the target? A Service, Plugin, Entity...
     * @param type of event. Consumed or not.
     * @param category What category the event target belongs to: System, Gameplay, UI...
     */
    public EventQueryParameters(Class<?> emittorClass,
                                Class<?> targetClass,
                                Event.Target target,
                                Event.Type type,
                                Event.Category category,
                                String name
    ){
        this.emittorClass = emittorClass    == null ? Event.ANY_CLASS       : emittorClass;
        this.targetClass = targetClass      == null ? Event.ANY_CLASS       : targetClass;
        this.target = target                == null ? Event.Target.ANY      : target;
        this.type = type                    == null ? Event.Type.ANY        : type;
        this.category = category            == null ? Event.Category.ANY    : category;
    }

    public Event.Category category(){
        return category;
    }

    public Event.Type type(){
        return type;
    }

    public Event.Target target(){
        return target;
    }

    public Class<?> emittorClass(){
        return emittorClass;
    }

    public Class<?> targetClass(){
        return targetClass;
    }
    public String name(){return name;}
}
