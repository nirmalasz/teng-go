package com.tenggo.frontend.strategy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.entities.Player;

public class RushStrategy implements AttackStrategy {

    private final float rushMultiplier = 2f;

    @Override
    public void attack(
        Enemy enemy,
        Player player,
        Array<Enemy> enemies,
        float delta
    ) {
        Vector2 direction =
            new Vector2(
                player.getPosition().x
                    - enemy.getPosition().x,

                player.getPosition().y
                    - enemy.getPosition().y
            );
        direction.nor();
        enemy.move(
            direction.x
                * enemy.getSpeed()
                * rushMultiplier
                * delta,
            direction.y
                * enemy.getSpeed()
                * rushMultiplier
                * delta,
            player,
            enemies
        );
    }
}
