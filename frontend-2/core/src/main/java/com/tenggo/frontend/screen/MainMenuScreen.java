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
import com.tenggo.frontend.states.MenuState;
import com.tenggo.frontend.ui.BackgroundRenderer;


public class MainMenuScreen implements Screen {
    private final TengGoGame game;
    private final Stage stage;
    private final SpriteBatch batch;
    private Texture logoTexture;

    public MainMenuScreen(TengGoGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        GameManager.getInstance()
            .changeState(new MenuState(this));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("ui/metal-ui.json"));

        Table table = new Table();
        table.setFillParent(true);

        Texture logoTexture = new Texture(Gdx.files.internal("logo/tenggologo.png"));
        Image logo = new Image(logoTexture);

        TextField usernameField = new TextField("", skin);

        usernameField.setMessageText("Enter username");

        TextButton startButton = new TextButton("Start Game", skin);

        startButton.addListener(event -> {
            if (!startButton.isPressed()) return false;
            String username = usernameField.getText();

            if (!username.isEmpty()) {
                GameManager.getInstance().registerPlayer(username);
                game.setScreen(new PreparationScreen(game));
            }
            return true;
        });

        table.add(logo)
            .width(400)
            .height(200)
            .padBottom(30);
        table.row();

        table.add(usernameField)
            .width(300)
            .padBottom(20);

        table.row();

        table.add(startButton)
            .width(200);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BackgroundRenderer.render(batch);
        GameManager.getInstance().update(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {
        GameManager.getInstance().getBGMManager().play("menumusic", true, "mp3");
    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        logoTexture.dispose();
        stage.dispose();

    }

}
