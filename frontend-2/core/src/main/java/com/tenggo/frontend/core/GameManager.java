package com.tenggo.frontend.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.tenggo.frontend.states.GameState;
import com.tenggo.frontend.states.MenuState;

public class GameManager {
    private static GameManager instance;
    private boolean gameActive;
    private GameState currentState;
    // private LevelManager levelManager;
    private BackendService backendService;
    private String currentPlayerId;
    private int coinsCollected = 0;
    private int score = 0;
    private int highScore;
    private int totalCoins;
    private int highestLevelReached;

    private int hpUpgradeLevel = 0;
    private int damageUpgradeLevel = 0;
    private int speedUpgradeLevel = 0;
    private int dashUpgradeLevel = 0;
    private int deathDefianceLevel = 0;

    private int baseHp = 100;
    private int baseDamage = 10;
    private float baseSpeed = 200f;

    private GameManager(){
        // initialize game state, level manager, backend service, etc.
        backendService = new BackendService();
    }

    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public void changeState(GameState newState){
        if (currentState != null){
            currentState.exit();
        }
        currentState = newState;
        currentState.enter();
    }

    public void update(float delta){
        if (currentState != null){
            currentState.update(delta);
        }
    }

    public void render(){
        if (currentState != null){
            currentState.render();
        }
    }

    public void registerPlayer(String username){
        backendService.createPlayer(username, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JsonValue parseResult = new JsonReader().parse(response);

                    currentPlayerId = parseResult.getString("playerId");
                    Gdx.app.log("SUCCESS", "Player ID: " + currentPlayerId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onError(String error) {
                Gdx.app.log("ERROR", error);
            }
        });
    }

    public void fetchPlayerStats() {
        backendService.getPlayerStats(currentPlayerId,new BackendService.RequestCallback() {
                @Override
                public void onSuccess(String response) {JsonValue json =new JsonReader()
                            .parse(response);
                    highScore = json.getInt("highScore");
                    totalCoins = json.getInt("totalCoins");
                    highestLevelReached = json.getInt("highestLevelReached");
                }
                @Override
                public void onError(String error) {
                    Gdx.app.error("PLAYER STATS",error);
                }
            }
        );
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public int getScore() {
        return score;
    }

    public int getHighestLevelReached() {
        return highestLevelReached;
    }

    public void setHighestLevelReached(int stage){
        this.highestLevelReached = stage;
    }

    public void addCoins(int amount) {
        coinsCollected += amount;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getCurrentHp() {
        return baseHp + (hpUpgradeLevel * 20);
    }

    public int getCurrentDamage() {
        return baseDamage + (damageUpgradeLevel * 20);
    }

    public float getCurrentSpeed() {
        return baseSpeed + (speedUpgradeLevel * 25f);
    }

    public int getDashCount() {
        return 1 + dashUpgradeLevel;
    }

    public int getDeathDefianceCount() {
        return deathDefianceLevel;
    }

    public boolean upgradeHp() {
        if (coinsCollected < 20) {
            return false;
        }
        coinsCollected -= 20;
        hpUpgradeLevel++;
        return true;
    }
    public boolean upgradeDamage() {
        if (coinsCollected < 30) {
            return false;
        }
        coinsCollected -= 30;
        damageUpgradeLevel++;
        return true;
    }
    public boolean upgradeSpeed() {
        if (coinsCollected < 30) {
            return false;
        }
        coinsCollected -= 30;
        speedUpgradeLevel++;
        return true;
    }
    public boolean upgradeDash() {
        if (coinsCollected < 100) {
            return false;
        }
        coinsCollected -= 100;
        dashUpgradeLevel++;
        return true;
    }

    public boolean upgradeDeathDefiance() {
        if (coinsCollected < 200) {
            return false;
        }
        coinsCollected -= 200;
        deathDefianceLevel++;
        return true;
    }

    public void startGame(){
        gameActive = true;
        System.out.println("Game Started!");
        score = 0;
    }

    public void endGame(int lastStage){
        if (currentPlayerId == null){
            Gdx.app.log("ERROR", "Cannot submit score");
            return;
        }

        backendService.submitScore(
            currentPlayerId,
            score,
            coinsCollected,
            lastStage,
            new BackendService.RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    Gdx.app.log("SUBMIT SCORE SUCCESS", response);
                }

                @Override
                public void onError(String error) {
                    Gdx.app.error("SUBMIT SCORE ERROR", error);
                }
            }
        );
    }

}
