package com.tenggo.frontend.observer;

import com.badlogic.gdx.Gdx;

public class PlayerHpObserver implements Observer {
    private int currentHp = 100;

    @Override
    public void update(int hp) {
        this.currentHp = hp;
        Gdx.app.log("PLAYER HP","Current HP: " + hp);
    }

    public int getCurrentHp() {
        return currentHp;
    }
}
