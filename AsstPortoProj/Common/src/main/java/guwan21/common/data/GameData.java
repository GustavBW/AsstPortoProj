package guwan21.common.data;

import guwan21.common.events.Event;
import guwan21.common.events.IEventBroker;
import guwan21.common.util.SPILocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class GameData {

    private float delta;
    private int displayWidth;

    private double msFromGameStart;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();

    private final IEventBroker broker = SPILocator.getBean(IEventBroker.class);

    public double getMsFromGameStart() {
        return msFromGameStart;
    }

    public void setMsFromGameStart(double msFromGameStart) {
        this.msFromGameStart = msFromGameStart;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public IEventBroker getBroker(){
        return broker;
    }
}
