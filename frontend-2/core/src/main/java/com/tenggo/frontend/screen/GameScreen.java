package com.tenggo.frontend.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.tenggo.frontend.TengGoGame;
import com.tenggo.frontend.command.*;
import com.tenggo.frontend.core.GameManager;
import com.tenggo.frontend.core.GameStats;
import com.tenggo.frontend.entities.Bullet;
import com.tenggo.frontend.entities.Player;
import com.tenggo.frontend.observer.GameStatsObserver;
import com.tenggo.frontend.pool.BulletPool;
import com.tenggo.frontend.states.PlayingState;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Intersector;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.strategy.*;
import com.tenggo.frontend.observer.PlayerHpObserver;



public class GameScreen implements Screen {

    private final TengGoGame game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Texture arenaBackground;
    private Rectangle playArea;

    private Player player;
    private Array<Enemy> enemies;

    private InputHandler inputHandler;

    private BulletPool bulletPool;

    private GameStats gameStats;

    private int stage;

    public GameScreen(
        TengGoGame game,
        Player player,
        int stage) {

        this.player = player;
        this.stage = stage;
        this.game = game;
        GameManager.getInstance()
            .changeState(new PlayingState(this));
    }

    @Override
    public void show() {
        System.out.println("Gameplay Started");
        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        arenaBackground = new Texture(Gdx.files.internal("bg-battle.png"));

        playArea = new Rectangle(125, 80, 775, 540);

        player.setX(playArea.x + playArea.width / 2);
        player.setY(playArea.y + playArea.height / 2);

        bulletPool = new BulletPool(20);
        enemies = new Array<>();

        for (int i = 0; i < stage + 1; i++) {
            float rx = MathUtils.random(playArea.x, playArea.x + playArea.width - 50);
            float ry = MathUtils.random(playArea.y, playArea.y + playArea.height - 50);
            enemies.add(new Enemy(rx, ry, new MeleeStrategy()));
        }
        if (stage >= 2) {
            float rx = MathUtils.random(playArea.x, playArea.x + playArea.width - 50);
            float ry = MathUtils.random(playArea.y, playArea.y + playArea.height - 50);
            enemies.add(new Enemy(rx, ry, new RangedStrategy(bulletPool)));
        }
        if (stage >= 3) {
            float rx = MathUtils.random(playArea.x, playArea.x + playArea.width - 50);
            float ry = MathUtils.random(playArea.y, playArea.y + playArea.height - 50);
            enemies.add(new Enemy(rx, ry, new RushStrategy()));
        }

        gameStats = new GameStats();
        gameStats.addObserver(new GameStatsObserver());

        inputHandler = new InputHandler(
            new MoveUpCommand(player),
            new MoveDownCommand(player),
            new MoveLeftCommand(player),
            new MoveRightCommand(player),
            new AttackCommand(player, enemies),
            new DashCommand(player)
        );

        player.addObserver(
            new PlayerHpObserver()
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameManager.getInstance().update(delta);
        inputHandler.handleInput(delta);

        player.update(delta);
        keepInsideArena(player);

        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(delta, player, enemies);
            keepInsideArena(enemy);
            if (enemy.getHitbox()
                .overlaps(player.getHitbox())) {
                player.takeDamage(10);
            }
        }

        for (int i = enemies.size - 1; i >= 0; i--) {
            if (enemies.get(i).isDead()) {
                gameStats.addEnemyKill();
                gameStats.addCoins(10);
                GameManager.getInstance().addCoins(10);
                GameManager.getInstance().addScore(100);
                enemies.removeIndex(i);
            }
        }

        for (int i = 0; i < bulletPool.getBullets().size; i++) {
            Bullet bullet = bulletPool.getBullets().get(i);
            bullet.update(delta);
            if (bullet.isActive() &&bullet.getHitbox().overlaps(player.getHitbox())) {
                player.takeDamage(10);
                bullet.deactivate();
            }
        }

        batch.begin();
        batch.draw(arenaBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Enemy enemy : enemies) {
            enemy.render(shapeRenderer);
        }
        for (int i = 0; i < bulletPool.getBullets().size; i++) {
            bulletPool.getBullets()
                .get(i)
                .render(shapeRenderer);
        }

        shapeRenderer.end();

        if (enemies.size == 0) {
            if (stage > GameManager.getInstance().getHighestLevelReached()) {

                GameManager.getInstance().setHighestLevelReached(stage);
            }
            if (stage >= 5){
                GameManager.getInstance().endGame(stage);
                Screen currentScreen = game.getScreen();
                game.setScreen(new WinScreen(game));
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
                return;
            } else {
                Screen currentScreen = game.getScreen();
                game.setScreen(new BlessingScreen(game,player,stage + 1));
                if (currentScreen != null) {
                    currentScreen.dispose();
                }
            }
        }

        if (player.isDead()) {
            if (stage > GameManager.getInstance().getHighestLevelReached()) {
                GameManager.getInstance().setHighestLevelReached(stage);
            }
            GameManager.getInstance().endGame(stage);
            Screen currentScreen = game.getScreen();
            game.setScreen(new DeathScreen(game));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
        }

        // TEMP DEBUG
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            Screen currentScreen = game.getScreen();
            game.setScreen(new DeathScreen(game));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            Screen currentScreen = game.getScreen();
            game.setScreen(new WinScreen(game));
            if (currentScreen != null) {
                currentScreen.dispose();
            }
        }
    }

    // Checks bounds for the Player
    private void keepInsideArena(Player player) {
        Rectangle hitbox = player.getHitbox();

        if (hitbox.x < playArea.x) {
            player.setX(playArea.x);
        } else if (hitbox.x + hitbox.width > playArea.x + playArea.width) {
            player.setX(playArea.x + playArea.width - hitbox.width);
        }

        if (hitbox.y < playArea.y) {
            player.setY(playArea.y);
        } else if (hitbox.y + hitbox.height > playArea.y + playArea.height) {
            player.setY(playArea.y + playArea.height - hitbox.height);
        }
    }

    // Checks bounds for the Enemy
    private void keepInsideArena(Enemy enemy) {
        Rectangle hitbox = enemy.getHitbox();

        if (hitbox.x < playArea.x) {
            enemy.setX(playArea.x);
        } else if (hitbox.x + hitbox.width > playArea.x + playArea.width) {
            enemy.setX(playArea.x + playArea.width - hitbox.width);
        }

        if (hitbox.y < playArea.y) {
            enemy.setY(playArea.y);
        } else if (hitbox.y + hitbox.height > playArea.y + playArea.height) {
            enemy.setY(playArea.y + playArea.height - hitbox.height);
        }
    }



    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (batch != null) batch.dispose();
        if (arenaBackground != null) arenaBackground.dispose();
    }
}
