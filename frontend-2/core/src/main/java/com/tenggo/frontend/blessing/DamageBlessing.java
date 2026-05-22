package com.tenggo.frontend.blessing;

import com.tenggo.frontend.entities.Player;

public class DamageBlessing implements Blessing {
    @Override
    public String getName() {
        return "Revolutionary Strength";
    }

    @Override
    public String getDescription() {
        return "+5 Damage";
    }

    @Override
    public void apply(Player player) {
        player.increaseDamage(5);
    }
}
