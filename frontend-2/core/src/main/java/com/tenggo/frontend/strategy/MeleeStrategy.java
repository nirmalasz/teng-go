package com.tenggo.frontend.strategy;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.entities.Player;

public class MeleeStrategy implements AttackStrategy {
    private enum State { CHASING, WINDUP, COOLDOWN }

    private final int attackDamage = 10;

    private State currentState = State.CHASING;
    private float stateTimer = 0f;

    private final float rushMultiplier = 1f;

    private final float windupTime = 0.5f;   // 0.5 second warning before damage
    private final float cooldownTime = 0.5f;

    @Override
    public void attack(
        Enemy enemy,
        Player player,
        Array<Enemy> enemies,
        float delta
    ) {
        Rectangle attackRange = new Rectangle(
            enemy.getHitbox().x - 4,
            enemy.getHitbox().y - 4,
            enemy.getHitbox().width + 8,
            enemy.getHitbox().height + 8
        );

        switch (currentState) {
            case CHASING:
                enemy.setAttacking(false);
                if (attackRange.overlaps(player.getHitbox())) {
                    currentState = State.WINDUP;
                    stateTimer = windupTime;
                    enemy.setAttacking(true);
                } else {
                    Vector2 direction = new Vector2(
                        player.getPosition().x - enemy.getPosition().x,
                        player.getPosition().y - enemy.getPosition().y
                    ).nor();

                    enemy.move(
                        direction.x * enemy.getSpeed() * rushMultiplier * delta,
                        direction.y * enemy.getSpeed() * rushMultiplier * delta,
                        player,
                        enemies
                    );
                }
                break;

            case WINDUP:
                stateTimer -= delta;

                if (stateTimer <= 0) {
                    if (attackRange.overlaps(player.getHitbox())) {
                        player.takeDamage(attackDamage);
                    }

                    enemy.setAttacking(false);
                    currentState = State.COOLDOWN;
                    stateTimer = cooldownTime;
                }
                break;

            case COOLDOWN:
                stateTimer -= delta;
                if (stateTimer <= 0) {
                    currentState = State.CHASING;
                }
                break;
        }
    }
}
