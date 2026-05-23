package com.tenggo.frontend.strategy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Bullet;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.entities.Player;
import com.tenggo.frontend.pool.BulletPool;

public class RangedStrategy implements AttackStrategy {

    private final float preferredDistance = 120f;
    private final BulletPool bulletPool;

    private float shootCooldown = 0f;

    public RangedStrategy(BulletPool bulletPool) {
        this.bulletPool = bulletPool;
    }


    @Override
    public void attack(
        Enemy enemy,
        Player player,
        Array<Enemy> enemies,
        float delta
    ) {
        shootCooldown -= delta;

        Vector2 direction =
            new Vector2(
                player.getPosition().x
                    - enemy.getPosition().x,
                player.getPosition().y
                    - enemy.getPosition().y
            );

        float distance = direction.len();
        direction.nor();

        // keep distance
        if (distance < preferredDistance) {
            enemy.move(
                -direction.x * enemy.getSpeed() * delta,
                -direction.y * enemy.getSpeed() * delta,
                player,
                enemies
            );
        }
        else if (distance > preferredDistance + 20) {
            enemy.move(
                direction.x * enemy.getSpeed() * delta,
                direction.y * enemy.getSpeed() * delta,
                player,
                enemies
            );
        }

        // projectile attack
        if (shootCooldown <= 0) {
            Bullet bullet = bulletPool.obtain();
            bullet.activate(
                enemy.getPosition().x,
                enemy.getPosition().y,
                direction
            );

            shootCooldown = 1.5f;
        }
    }
}
