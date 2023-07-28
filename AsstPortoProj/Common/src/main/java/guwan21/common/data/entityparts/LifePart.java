package guwan21.common.data.entityparts;

import guwan21.common.data.GameData;
import guwan21.common.data.entities.Entity;

public class LifePart implements EntityPart {

    private int life;
    private boolean isHit, dead;
    private float expiration;

    public LifePart(int life, float expiration) {
        this.life = life;
        this.expiration = expiration;
        this.isHit = false;
        this.dead = false;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public float getExpiration() {
        return expiration;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }  
    
    public void reduceExpiration(float delta){
        this.expiration -= delta;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        reduceExpiration(gameData.getDelta());
        if(expiration <= 0){
            this.dead = true;
            return;
        }
        if (this.isHit) {
            this.life -= 1;
            this.isHit = false;
        }
        if (this.life <= 0) {
            this.dead = true;
        }
    }
}
