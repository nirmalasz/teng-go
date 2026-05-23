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
import com.tenggo.frontend.blessing.Blessing;
import com.tenggo.frontend.blessing.BlessingManager;
import com.tenggo.frontend.core.GameManager;
import com.tenggo.frontend.entities.Player;

import java.util.List;

public class BlessingScreen implements Screen {

    private final TengGoGame game;
    private final Player player;
    private final int nextStage;
    private final Stage stageUI;

    private final SpriteBatch batch;
    private final Texture backgroundTexture;

    public BlessingScreen(
        TengGoGame game,
        Player player,
        int nextStage
    ) {

        this.game = game;
        this.player = player;
        this.nextStage = nextStage;

        stageUI = new Stage(
            new ScreenViewport()
        );

        batch = new SpriteBatch();
        backgroundTexture = new Texture("bg-tenggo-office-blue.png");
        Gdx.input.setInputProcessor(stageUI);

        Skin skin = new Skin(Gdx.files.internal("ui/metal-ui.json"));

        Table table = new Table();
        table.setFillParent(true);

        Window statsWindow = new Window("Choose Blessing", skin);
        statsWindow.add(new Label("This blessing will expire after this run" , skin));
        statsWindow.row();

        table.add(statsWindow)
            .top()
            .center()
        .padBottom(50);



        table.row();

        BlessingManager blessingManager = new BlessingManager();

        List<Blessing> blessings = blessingManager.getRandomBlessings();

        for (Blessing blessing : blessings) {
            TextButton button =
                new TextButton(blessing.getName()
                        + "\n"
                        + blessing.getDescription(),
                    skin
                );

            button.addListener(event -> {
                if (!button.isPressed()) {
                    return false;
                }

                blessing.apply(player);
                Screen currentScreen = game.getScreen();
                game.setScreen(
                    new GameScreen(
                        game,
                        player,
                        nextStage
                    )
                );

                if (currentScreen != null) {
                    currentScreen.dispose();
                }

                return true;
            });

            table.add(button)
                .width(300)
                .padBottom(20);

            table.row();
        }
        stageUI.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stageUI.act(delta);
        stageUI.draw();
    }

    @Override public void show() {}

    @Override
    public void resize(int width, int height) {
        stageUI.getViewport().update(width, height, true);
    }

    @Override public void pause() {}

    @Override public void resume() {}

    @Override public void hide() {}

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stageUI.dispose();
        batch.dispose();
        backgroundTexture.dispose();
    }
}
