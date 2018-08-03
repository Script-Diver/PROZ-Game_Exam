package events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class HitEvent extends Event {
    private int damage;
    public static final EventType<HitEvent> HIT = new EventType<>(Event.ANY, "HIT");

    public HitEvent(Object source, EventTarget target, int dmg){
        super(source, target, HIT);
        damage = dmg;
    }

    @Override
    public EventType<? extends HitEvent> getEventType() {
        return (EventType<? extends HitEvent>) super.getEventType();
    }

    public int getDamage() {
        return damage;
    }
}
