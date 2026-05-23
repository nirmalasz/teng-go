package com.tenggo.frontend.observer;

import com.badlogic.gdx.Gdx;

public class GameStatsObserver implements Observer {
    @Override
    public void update(int value) {
        Gdx.app.log("GAME STATS","Updated Score: " + value);
    }
}
