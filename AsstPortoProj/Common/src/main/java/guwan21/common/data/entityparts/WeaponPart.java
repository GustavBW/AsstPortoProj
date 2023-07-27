package guwan21.common.data.entityparts;

import guwan21.common.data.Entity;
import guwan21.common.data.GameData;

public class WeaponPart implements  EntityPart {
    private float cooldownTime;
    private float cooldown;
    private boolean firing;


    public WeaponPart(float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public void setFiring(boolean firing) {
        if (!firing) {
            this.firing = false;
            return;
        }

        if (cooldown > 0) {
            this.firing = false;
            return;
        }

        this.firing = true;
        this.cooldown = this.cooldownTime;
    }

    public boolean isFiring() {
        return this.firing;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (this.cooldown > 0) {
            this.cooldown -= gameData.getDelta();
            this.firing = false;
        } else {
            this.cooldown = 0;
        }
    }
}
