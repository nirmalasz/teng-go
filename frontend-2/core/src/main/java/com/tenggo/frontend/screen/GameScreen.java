package com.tenggo.frontend.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

        bulletPool = new BulletPool(20);

        enemies = new Array<>();

        for (int i = 0; i < stage + 1; i++) {
            enemies.add(new Enemy(500 + i * 30,300,new MeleeStrategy()));
        }
        if (stage >= 2) {
            enemies.add(new Enemy(600,400,new RangedStrategy(bulletPool)));
        }
        if (stage >= 3) {
            enemies.add(new Enemy(700,350,new RushStrategy()));
        }

        gameStats = new GameStats();
        gameStats.addObserver(new GameStatsObserver());

        inputHandler = new InputHandler(
            new MoveUpCommand(player),
            new MoveDownCommand(player),
            new MoveLeftCommand(player),
            new MoveRightCommand(player),
            new AttackCommand(player, enemies)
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

        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(delta, player, enemies);

            if (enemy.getHitbox()
                .overlaps(player.getHitbox())) {
                player.takeDamage(10);
            }
        }

        for (int i = enemies.size - 1; i >= 0; i--) {
            if (enemies.get(i).isDead()) {
                gameStats.addEnemyKill();
                gameStats.addCoins(10);
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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        player.render(shapeRenderer);
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
            game.setScreen(new BlessingScreen(game,player,stage + 1));
        }

        if (player.isDead()) {
            game.setScreen(new DeathScreen(game));
        }

        // TEMP DEBUG
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            game.setScreen(new DeathScreen(game));
        }
    }



    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
