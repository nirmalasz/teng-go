package com.tenggo.frontend.blessing;

import com.tenggo.frontend.entities.Player;

public class SpeedBlessing implements Blessing {
    @Override
    public String getName() {
        return "Overtime Escape";
    }

    @Override
    public String getDescription() {
        return "+50 Speed";
    }

    @Override
    public void apply(Player player) {
        player.increaseSpeed(50f);
    }
}
