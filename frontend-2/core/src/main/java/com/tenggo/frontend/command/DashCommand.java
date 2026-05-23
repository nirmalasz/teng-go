package com.tenggo.frontend.command;

import com.tenggo.frontend.entities.Player;

public class DashCommand implements Command {
    private final Player player;

    public DashCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute(float delta) {
        player.dash();
    }
}
