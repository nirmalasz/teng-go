package com.tenggo.frontend.states;

import com.tenggo.frontend.screen.GameScreen;

public class PlayingState implements GameState {

    private final GameScreen screen;

    public PlayingState(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void enter() {

        System.out.println("Entering Playing State");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void exit() {

        System.out.println("Exiting Playing State");
    }
}
