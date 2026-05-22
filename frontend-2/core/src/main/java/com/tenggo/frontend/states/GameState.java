package com.tenggo.frontend.states;

public interface GameState {
    void enter();
    void update(float deltaTime);
    void render();
    void exit();
}
