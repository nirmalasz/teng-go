package com.tenggo.frontend.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.tenggo.frontend.TengGoGame;
import com.tenggo.frontend.achievement.Achievement;
import com.tenggo.frontend.achievement.AchievementManager;
import com.tenggo.frontend.core.GameManager;
import com.tenggo.frontend.states.AchievementState;
import com.tenggo.frontend.ui.BackgroundRenderer;

public class AchievementScreen implements Screen {
    private final TengGoGame game;
    private final Stage stage;
    private final SpriteBatch batch;
    private AchievementManager achievementManager;

    public AchievementScreen(TengGoGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());



        GameManager.getInstance().changeState(new AchievementState());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("ui/metal-ui.json"));

        Table table = new Table();
        table.setFillParent(true);

        Window achievementWindow = new Window("Achievement List", skin);
        Label loadingLabel = new Label("Fetching from server...", skin);
        achievementWindow.add(loadingLabel).pad(20);
        achievementWindow.pack();

        GameManager.getInstance().fetchUnlockedAchievements(
            new GameManager.UnlockedFetchCallback() {
                @Override
                public void onSuccess(Array<String> unlockedAchievementID) {
                    Array<Achievement> masterList = AchievementManager.getAchievements();
                    achievementWindow.clearChildren();

                    for(Achievement achievement: masterList){
                        if (unlockedAchievementID.contains(achievement.getID(), false)) {
                            achievement.setUnlocked(true);
                        }
                        String status = achievement.isUnlocked() ? "[UNLOCKED]" : "[LOCKED]";
                        Label label = new Label(
                            status + " " + achievement.getTitle() + ": " + achievement.getDescription() + " " + achievement.getReward(),
                            skin
                        );
                        label.setWrap(true);
                        achievementWindow.add(label).width(500).padBottom(15);
                        achievementWindow.row();
                    }
                    achievementWindow.pack();
                }

                @Override
                public void onError(String error) {
                    achievementWindow.clearChildren();
                    achievementWindow.add(new Label("Failed to load: " + error, skin)).pad(20);
                    achievementWindow.pack();
                }
            });


        TextButton backButton = new TextButton("Back",skin);
        backButton.addListener(event -> {
            if (!backButton.isPressed()) {
                return false;
            }

            Screen currentScreen = game.getScreen();
            game.setScreen(new PreparationScreen(game));
            if(currentScreen!=null) {
                currentScreen.dispose();
            }
            return true;
        });

        table.add(achievementWindow).padBottom(30);
        table.row();
        table.add(backButton).width(200);

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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {}

    @Override public void hide() {}

    @Override public void pause() {}

    @Override public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
