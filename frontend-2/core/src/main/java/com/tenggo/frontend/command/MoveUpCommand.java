package com.tenggo.frontend.command;

import com.tenggo.frontend.entities.Player;

public class MoveUpCommand implements Command {
    private final Player player;

    public MoveUpCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute(float delta) {
        player.move(0, player.getSpeed() * delta);
    }
}
