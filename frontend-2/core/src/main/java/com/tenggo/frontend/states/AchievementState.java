package com.tenggo.frontend.states;

public class AchievementState implements GameState {
    @Override
    public void enter() {
        System.out.println("Entering Achievement State");
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render() {
    }

    @Override
    public void exit() {
        System.out.println("Exiting Achievement State");
    }
}
