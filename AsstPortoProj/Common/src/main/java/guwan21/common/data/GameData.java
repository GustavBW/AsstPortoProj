package guwan21.common.data;

import guwan21.common.events.IEventMediatorService;
import guwan21.common.util.SPILocator;

public class GameData {

    private float delta;
    private int displayWidth;

    private double msFromGameStart;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();

    private final IEventMediatorService broker = SPILocator.getBean(IEventMediatorService.class);

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

    public IEventMediatorService getBroker(){
        return broker;
    }
}
