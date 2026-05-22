package com.tenggo.frontend.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tenggo.frontend.TengGoGame;
import com.tenggo.frontend.core.GameManager;
import com.tenggo.frontend.entities.Player;
import com.tenggo.frontend.states.PreparationState;

public class PreparationScreen implements Screen {
    private final TengGoGame game;
    private final Stage stage;

    public PreparationScreen(TengGoGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        GameManager.getInstance()
            .changeState(new PreparationState(this));

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Table table = new Table();
        table.setFillParent(true);
        Label title =
            new Label("Preparation Room", skin);

        Label stats =
            new Label(
                "HP: 100\nDamage: 10\nCoins: 0",
                skin
            );

        TextButton hpUpgrade =
            new TextButton("Upgrade HP", skin);
        TextButton dmgUpgrade =
            new TextButton("Upgrade Damage", skin);
        TextButton playButton =
            new TextButton("Play", skin);

        playButton.addListener(event -> {
            if (!playButton.isPressed()) return false;
            game.setScreen(new GameScreen(game, new Player(200, 200), 1));
            return true;
        });

        table.add(title).padBottom(30);
        table.row();

        table.add(stats).padBottom(20);
        table.row();

        table.add(hpUpgrade).padBottom(10);
        table.row();

        table.add(dmgUpgrade).padBottom(30);
        table.row();

        table.add(playButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        GameManager.getInstance().update(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
