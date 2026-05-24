package com.tenggo.frontend.strategy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Bullet;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.entities.Player;
import com.tenggo.frontend.pool.BulletPool;

public class RangedStrategy implements AttackStrategy {
    private enum State { POSITIONING, WINDUP, COOLDOWN }

    private State currentState = State.POSITIONING;
    private float stateTimer = 0f;

    private final float preferredDistance = 120f;
    private final float maxDistance = 180f;

    private final BulletPool bulletPool;

    private final float windupTime = 0.5f;
    private final float cooldownTime = 1.0f;

    public RangedStrategy(BulletPool bulletPool) {
        this.bulletPool = bulletPool;
    }

    @Override
    public void attack(Enemy enemy, Player player, Array<Enemy> enemies, float delta) {

        Vector2 direction = new Vector2(
            player.getPosition().x - enemy.getPosition().x,
            player.getPosition().y - enemy.getPosition().y
        );
        float distance = direction.len();
        direction.nor();

        switch (currentState) {
            case POSITIONING:
                enemy.setAttacking(false);

                if (distance < preferredDistance) {
                    enemy.move(
                        -direction.x * enemy.getSpeed() * delta,
                        -direction.y * enemy.getSpeed() * delta,
                        player,
                        enemies
                    );
                } else if (distance > maxDistance) {
                    enemy.move(
                        direction.x * enemy.getSpeed() * delta,
                        direction.y * enemy.getSpeed() * delta,
                        player,
                        enemies
                    );
                } else {
                    currentState = State.WINDUP;
                    stateTimer = windupTime;
                    enemy.setAttacking(true);
                }
                break;

            case WINDUP:
                stateTimer -= delta;

                if (stateTimer <= 0) {
                    Bullet bullet = bulletPool.obtain();

                    Vector2 fireDirection = new Vector2(
                        player.getPosition().x - enemy.getPosition().x,
                        player.getPosition().y - enemy.getPosition().y
                    ).nor();

                    bullet.activate(
                        enemy.getPosition().x,
                        enemy.getPosition().y,
                        fireDirection
                    );

                    enemy.setAttacking(false);
                    currentState = State.COOLDOWN;
                    stateTimer = cooldownTime;
                }
                break;

            case COOLDOWN:
                stateTimer -= delta;
                if (stateTimer <= 0) {
                    currentState = State.POSITIONING;
                }
                break;
        }
    }
}
