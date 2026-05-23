package com.tenggo.frontend.command;

import com.tenggo.frontend.entities.Player;

public class MoveRightCommand implements Command {

    private final Player player;

    public MoveRightCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute(float delta) {
        player.move(player.getSpeed() * delta, 0);
    }
}
