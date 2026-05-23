package com.tenggo.frontend.core;

import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.observer.Observer;
import com.tenggo.frontend.observer.Subject;

public class GameStats implements Subject {
    private int coins = 0;
    private int enemiesDefeated = 0;
    private int score = 0;

    private final Array<Observer> observers;

    public GameStats() {
        observers = new Array<>();
    }

    public void addCoins(int amount) {
        coins += amount;
        notifyObservers();
    }

    public void addEnemyKill() {
        enemiesDefeated++;
        score += 100;

        notifyObservers();
    }

    public int getCoins() {
        return coins;
    }

    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void addObserver(Observer observer) {

        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

        observers.removeValue(observer, true);
    }

    @Override
    public void notifyObservers() {
        // for now only score, later need for coin and enemy hp
        for (int i = 0; i < observers.size; i++) {
            observers.get(i).update(score);
        }
    }
}
