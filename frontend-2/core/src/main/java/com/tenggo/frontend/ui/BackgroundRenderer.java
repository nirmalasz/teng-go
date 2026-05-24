package com.tenggo.frontend.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundRenderer {
    private static final Texture background =
        new Texture(Gdx.files.internal("bg-tenggo-office.png"));

    public static void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(
            background,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );
        batch.end();
    }
}
