package com.tenggo.frontend.core;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;

import java.util.UUID;

public class BackendService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private UUID currentPlayerId;

    public interface RequestCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public void createPlayer(String username, RequestCallback callback) {
        String json = "{\"username\":\"" + username + "\"}";


        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url(BASE_URL + "/players")
            .header("Content-Type", "application/json")
            .content(json)
            .build();


        sendRequest(request, callback);
    }

    public void submitScore(String playerId, int scoreValue, int coinsCollected, int stageReached,
        RequestCallback callback) {
        String json = String.format(
            "{\"playerId\":\"%s\",\"value\":%d,\"coinsCollected\":%d,\"levelReached\":%d}",
            playerId, scoreValue, coinsCollected, stageReached
        );


        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url(BASE_URL + "/scores")
            .header("Content-Type", "application/json")
            .content(json)
            .build();

        sendRequest(request, callback);
    }

    public void getPlayerStats(String username, RequestCallback callback) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest()
            .method(Net.HttpMethods.GET)
            .url(BASE_URL + "/players/" + "/username" + username)
            .build();

        sendRequest(request, callback);
    }

    public void unlockAchievement(String playerId, String achievementId, RequestCallback callback) {
        String json = String.format(
            "{\"playerId\":\"%s\",\"achievementId\":\"%s\"}",
            playerId, achievementId
        );

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url(BASE_URL + "/player-achievements")
            .header("Content-Type", "application/json")
            .content(json)
            .build();

        sendRequest(request, callback);
    }

    public void getPlayerAchievements(String playerId, RequestCallback callback) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest request = requestBuilder.newRequest()
            .method(Net.HttpMethods.GET)
            .url(BASE_URL + "/player-achievements/" + "player/" + playerId)
            .build();

        sendRequest(request, callback);
    }

    private void sendRequest(Net.HttpRequest request, RequestCallback callback) {
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String result = httpResponse.getResultAsString();
                int statusCode = httpResponse.getStatus().getStatusCode();


                if (statusCode >= 200 && statusCode < 300) {
                    callback.onSuccess(result);
                } else {
                    Gdx.app.error("BackendService", "Error Code: " + statusCode + ", Msg: " + result);
                    callback.onError("Server Error: " + statusCode);
                }
            }


            @Override
            public void failed(Throwable t) {
                callback.onError(t.getMessage());
            }


            @Override
            public void cancelled() {
                callback.onError("Request cancelled");
            }
        });
    }


}
