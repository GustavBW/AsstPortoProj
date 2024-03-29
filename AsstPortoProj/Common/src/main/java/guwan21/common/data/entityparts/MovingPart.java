package guwan21.common.data.entityparts;

import guwan21.common.data.GameData;
import guwan21.common.data.entities.Entity;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MovingPart
        implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float startSpeed, maxSpeed, rotationSpeed;
    private boolean left, right, up;
    private boolean startSpeedSet;

    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed, float startSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.startSpeed = startSpeed;
        this.startSpeedSet = !(startSpeed > 0);
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }
    public float getDeceleration(){
        return deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }
    public float getAcceleration(){
        return acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public float getMaxSpeed(){return maxSpeed;}
    public float getStartSpeed(){return startSpeed;}

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
    public float getRotationSpeed(){
        return rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public float getSpeed() {
        return (float) sqrt(dx * dx + dy * dy);
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        if (!this.startSpeedSet) {
            dx = (float) (Math.cos(radians) * this.startSpeed);
            dy = (float) (Math.sin(radians) * this.startSpeed);
            this.startSpeedSet = true;
        }

        // turning
        if (left) {
            radians += rotationSpeed * dt;
        }

        if (right) {
            radians -= rotationSpeed * dt;
        }

        // accelerating            
        if (up) {
            dx += cos(radians) * acceleration * dt;
            dy += sin(radians) * acceleration * dt;
        }

        // deccelerating
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        }
        else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += dy * dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        }
        else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
