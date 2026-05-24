package com.tenggo.frontend.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tenggo.frontend.TengGoGame;
import com.tenggo.frontend.core.GameManager;
import com.tenggo.frontend.entities.Player;
import com.tenggo.frontend.states.PreparationState;
import com.tenggo.frontend.ui.BackgroundRenderer;

public class PreparationScreen implements Screen {
    private final TengGoGame game;
    private final Stage stage;
    private final SpriteBatch batch;

    public PreparationScreen(TengGoGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        GameManager.getInstance()
            .changeState(new PreparationState(this));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/metal-ui.json"));

        Table table = new Table();
        table.setFillParent(true);

        Label stats =
            new Label(
                "HP: 100" +
                    "\nDamage: 10" +
                    "\nCoins: 0",
                skin
            );

        // left panel
        Window statsWindow = new Window("Stats", skin);
        statsWindow.add(new Label("HP:" + GameManager.getInstance().getCurrentHp(), skin));
        statsWindow.row();
        statsWindow.add(new Label("Damage:" + GameManager.getInstance().getCurrentDamage(),
            skin));
        statsWindow.row();
        statsWindow.add(new Label("Speed:" + GameManager.getInstance().getCurrentSpeed(), skin));
        statsWindow.row();
        statsWindow.add(new Label("Dash Count:" + GameManager.getInstance().getDashCount(),
            skin));
        statsWindow.row();
        statsWindow.add(new Label("Death Defiance Count:" + GameManager.getInstance().getDeathDefianceCount(),
            skin));
        statsWindow.row();
        statsWindow.pack();

        TextButton hpUpgrade =
            new TextButton("Upgrade HP (+20)", skin);
        TextButton dmgUpgrade =
            new TextButton("Upgrade Damage (+20)", skin);
        TextButton speedUpgrade =
            new TextButton("Upgrade Speed (+25)", skin);
        TextButton dashUpgrade =
            new TextButton("Upgrade Dash (+1)", skin);
        TextButton dashDefianceUpgrade =
            new TextButton("Upgrade Death Defiance (+1)", skin);

        Table upgradeTable = new Table();
        upgradeTable.align(Align.center);

        // Add HP Row
        upgradeTable.add(createUpgradeRow(hpUpgrade, 20, skin)).padBottom(10);
        upgradeTable.row();

        // Add Damage Row
        upgradeTable.add(createUpgradeRow(dmgUpgrade, 30, skin)).padBottom(10);
        upgradeTable.row();

        // Add Speed Row
        upgradeTable.add(createUpgradeRow(speedUpgrade, 30, skin)).padBottom(10);
        upgradeTable.row();

        // Add Dash Row
        upgradeTable.add(createUpgradeRow(dashUpgrade, 100, skin)).padBottom(10);
        upgradeTable.row();

        // Add Defiance Row
        upgradeTable.add(createUpgradeRow(dashDefianceUpgrade, 200, skin)).padBottom(20);
        upgradeTable.row();

        TextButton playButton =
            new TextButton("Play", skin);
        TextButton achievementButton =
            new TextButton("Achievements",skin);

        // right panel
        Label progressionStats = new Label(
                "Highest Stage Reached: " + GameManager.getInstance().getHighestLevelReached() +
                "\n" +
                "Total Coins: " + GameManager.getInstance().getCoinsCollected(),
            skin
        );
        progressionStats.setAlignment(Align.right);

        Player player = new Player(200, 200);
        player.increaseMaxHp(GameManager.getInstance().getCurrentHp() - 100);
        player.increaseDamage(GameManager.getInstance().getCurrentDamage() - 10);
        player.increaseSpeed(GameManager.getInstance().getCurrentSpeed() - 200f);
        player.setDashCount(GameManager.getInstance().getDashCount());
        player.setDeathDefianceCount(GameManager.getInstance().getDeathDefianceCount());

        playButton.addListener(event -> {
            if (!playButton.isPressed()) return false;
            Screen currentScreen = game.getScreen();
            game.setScreen(new GameScreen(game, player, 1));
            if(currentScreen != null){
                currentScreen.dispose();
            }
            return true;
        });

        // achievement
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

        //upgrade button
        hpUpgrade.addListener(event -> {
            if (!hpUpgrade.isPressed()) {
                return false;
            }
            GameManager.getInstance().upgradeHp();
            Screen currentScreen = game.getScreen();
            game.setScreen(new PreparationScreen(game));
            if(currentScreen != null){
                currentScreen.dispose();
            }
            return true;
        });
        dmgUpgrade.addListener(event -> {
           if(!dmgUpgrade.isPressed()){
               return false;
           }
           GameManager.getInstance().upgradeDamage();
           Screen currentScreen = game.getScreen();
           game.setScreen(new PreparationScreen(game));
              if(currentScreen != null){
                currentScreen.dispose();
            }
              return true;
        });
        speedUpgrade.addListener(event -> {
            if (!speedUpgrade.isPressed()) {
                return false;
            }
            GameManager.getInstance().upgradeSpeed();
            Screen currentScreen = game.getScreen();
            game.setScreen(new PreparationScreen(game));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
            return true;
        });


        dashUpgrade.addListener(event -> {
            if (!dashUpgrade.isPressed()) {
                return false;
            }

            GameManager.getInstance().upgradeDash();
            Screen currentScreen = game.getScreen();
            game.setScreen(new PreparationScreen(game));

            if (currentScreen != null) {
                currentScreen.dispose();
            }
            return true;
        });

        dashDefianceUpgrade.addListener(event -> {
            if (!dashDefianceUpgrade.isPressed()) {
                return false;
            }
            GameManager.getInstance().upgradeDeathDefiance();
            Screen currentScreen = game.getScreen();
            game.setScreen(new PreparationScreen(game));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
            return true;
        });


        Table progressionTable = new Table();
        progressionTable.setBackground(skin.getDrawable("rect"));
        progressionTable.add(progressionStats)
                .right()
                .top();

        Table bottomTable = new Table();
        bottomTable.add(achievementButton)
                .width(250)
                .padRight(20);
        bottomTable.add(playButton)
                .width(250);
        table.row();

        table.add(statsWindow)
            .top()
            .left()
            .padRight(80);

        table.add(upgradeTable)
            .top()
            .left()
            .padRight(120);

        table.add(progressionTable)
            .top()
            .right();

        table.row();

        table.add(bottomTable)
            .colspan(3)
            .padTop(60);

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
        stage.dispose();
    }

    private Table createUpgradeRow(TextButton button, int cost, Skin skin){
        Table rowTable = new Table();
        rowTable.add(button).width(250);

        Table costRect = new Table();
        costRect.setBackground(skin.getDrawable("rect"));

        Label costLabel = new Label(cost + "C", skin);
        costRect.add(costLabel).padLeft(10).padRight(10).padTop(5).padBottom(5);

        rowTable.add(costRect).width(60).padLeft(10);

        return rowTable;
    }
}
