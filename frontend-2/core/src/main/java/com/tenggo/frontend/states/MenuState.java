package com.tenggo.frontend.states;

import com.tenggo.frontend.screen.MainMenuScreen;

public class MenuState implements  GameState{
    private final MainMenuScreen screen;

    public MenuState(MainMenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void enter() {
        System.out.println("Entering Menu State");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void exit() {
        System.out.println("Exiting Menu State");
    }
}
