package guwan21.common.events;

/**
 * @param emittorClass The class of the object that presumably emitted the event
 * @param targetClass What class the event targets
 * @param target What is the target? A Service, Plugin, Entity...
 * @param type of event. Consumed or not.
 * @param category What category the event target belongs to: System, Gameplay, UI...
 */
public record EventQueryParameters(Class<?> emittorClass,
                                   Class<?> targetClass,
                                   Event.Target target,
                                   Event.Type type,
                                   Event.Category category
) {}
