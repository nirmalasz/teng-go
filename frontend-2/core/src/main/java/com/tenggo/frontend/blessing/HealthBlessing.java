package com.tenggo.frontend.blessing;

import com.tenggo.frontend.entities.Player;

public class HealthBlessing implements Blessing {
    @Override
    public String getName() {
        return "Karl Marx Vitality";
    }

    @Override
    public String getDescription() {
        return "+20 HP";
    }

    @Override
    public void apply(Player player) {
        player.increaseMaxHp(20);
    }
}
