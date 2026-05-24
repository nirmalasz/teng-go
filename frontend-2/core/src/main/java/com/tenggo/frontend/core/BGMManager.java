package com.tenggo.frontend.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BGMManager {
    private Music currentMusic;
    private String currentTrackName = "";

    public void play(String trackName, boolean loop, String fileFormat) {
        if (currentTrackName.equals(trackName) && currentMusic != null && currentMusic.isPlaying()) {
            return;
        }

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("bgm/" + trackName
            + "." + fileFormat));
        currentMusic.setLooping(loop);
        currentMusic.setVolume(0.5f);
        currentMusic.play();
        currentTrackName = trackName;
    }

    public void stop() {
        if (currentMusic != null) currentMusic.stop();
    }

    public void dispose() {
        if (currentMusic != null) currentMusic.dispose();
    }
}
