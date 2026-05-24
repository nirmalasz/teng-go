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

public class WinScreen implements Screen {

    private final TengGoGame game;
    private final Stage stage;
    private final SpriteBatch batch;
    private final Texture backgroundTexture;

    public WinScreen(TengGoGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        backgroundTexture = new Texture("bg-tenggo-office-green.png");

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/metal-ui.json"));

        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("YOU ESCAPED THE OFFICE!",skin);

        Label stats = new Label(
            "Final Score: "+ GameManager.getInstance().getScore(),
                skin
            );

        stats.setAlignment(com.badlogic.gdx.utils.Align.center);

        Table statsBox = new Table();
        statsBox.setBackground(skin.getDrawable("rect"));
        statsBox.add(stats).pad(20);

        TextButton achievementButton = new TextButton("Achievements",skin);
        TextButton exitButton = new TextButton("Exit",skin);

        achievementButton.addListener(event -> {
            if (!achievementButton.isPressed()) {
                return false;
            }
            Screen currentScreen = game.getScreen();
            game.setScreen(new AchievementScreen(game));
            if (currentScreen != null) {
                currentScreen.dispose();
            }

            return true;
        });

        exitButton.addListener(event -> {
            if (!exitButton.isPressed()) {
                return false;
            }
            Gdx.app.exit();
            return true;
        });

        table.add(title).padBottom(40);
        table.row();

        table.add(statsBox).padBottom(40);
        table.row();

        table.add(achievementButton)
            .width(250)
            .padBottom(20);
        table.row();

        table.add(exitButton)
            .width(250);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0.2f,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {
        GameManager.getInstance().getBGMManager().play("winnerjingle", false, "mp3");
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
