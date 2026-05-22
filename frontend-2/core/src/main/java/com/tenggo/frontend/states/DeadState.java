package com.tenggo.frontend.states;

import com.tenggo.frontend.screen.DeathScreen;

public class DeadState implements GameState {

    private final DeathScreen screen;

    public DeadState(DeathScreen screen) {
        this.screen = screen;
    }

    @Override
    public void enter() {

        System.out.println("Entering Dead State");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void exit() {

        System.out.println("Exiting Dead State");
    }
}
