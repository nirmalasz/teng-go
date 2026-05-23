package com.tenggo.frontend.states;

import com.tenggo.frontend.screen.PreparationScreen;

public class PreparationState implements GameState {

    private final PreparationScreen screen;

    public PreparationState(PreparationScreen screen) {
        this.screen = screen;
    }

    @Override
    public void enter() {
        System.out.println("Entering Preparation State");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void exit() {
        System.out.println("Exiting Preparation State");
    }
}
