import guwan21.common.events.IEventBroker;

module Event{
    requires Common;
    provides IEventBroker with guwan21.event.EventBroker;
}