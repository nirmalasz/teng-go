package com.tenggo.frontend.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tenggo.frontend.TengGoGame;
import com.tenggo.frontend.core.GameManager;
import com.tenggo.frontend.states.DeadState;

public class DeathScreen implements Screen {

    private final TengGoGame game;
    private final Stage stage;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;

    public DeathScreen(TengGoGame game) {

        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        backgroundTexture = new Texture("bg-tenggo-office-red.png");

        GameManager.getInstance()
            .changeState(new DeadState(this));

        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("ui/metal-ui.json"));

        Table table = new Table();
        table.setFillParent(true);

        Label deadLabel =
            new Label("YOU DIED", skin);

        TextButton retryButton =
            new TextButton("Retry", skin);

        TextButton exitButton =
            new TextButton("Exit", skin);

        retryButton.addListener(event -> {

            if (!retryButton.isPressed()) return false;

            Screen currentScreen = game.getScreen();
            game.setScreen(new PreparationScreen(game));
            if(currentScreen != null) {
                currentScreen.dispose();
            }
            return true;
        });

        exitButton.addListener(event -> {

            if (!exitButton.isPressed()) return false;

            Gdx.app.exit();

            return true;
        });

        table.add(deadLabel).padBottom(110);
        table.row();

        table.add(retryButton)
            .width(200)
            .padBottom(10);

        table.row();

        table.add(exitButton)
            .width(200);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        GameManager.getInstance().update(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {
        GameManager.getInstance().getBGMManager().play("losejingle", false, "mp3");
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        backgroundTexture.dispose();
    }
}
