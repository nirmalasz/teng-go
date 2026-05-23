package com.tenggo.frontend.observer;

import com.badlogic.gdx.Gdx;

public class PlayerHpObserver implements Observer {
    @Override
    public void update(int hp) {
        Gdx.app.log("PLAYER HP","Current HP: " + hp);
    }
}
