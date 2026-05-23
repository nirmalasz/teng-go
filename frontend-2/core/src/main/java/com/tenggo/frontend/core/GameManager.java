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

    public void startGame(){
        gameActive = true;
        System.out.println("Game Started!");
        score = 0;
    }

    public void endGame(){
        if (currentPlayerId == null){
            Gdx.app.log("ERROR", "Cannot submit score");
            return;
        }
        //String playerId, int scoreValue, int currency, String lastStage
//        int coinsCollected = this.coinsCollected;
//        Integer score = scoreManager.getScore();
//        backendService.submitScore(currentPlayerId, score, coinsCollected, "",
//            new BackendService.RequestCallback() {
//                @Override
//                public void onSuccess(String response) {
//                    Gdx.app.log("SUBMIT SCORE SUCCESS", response);
//                }
//
//                @Override
//                public void onError(String error) {
//                    Gdx.app.error("SUBMIT SCORE ERROR", error);
//                }
//            });
    }

}
