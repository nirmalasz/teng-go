package com.tenggo.frontend.command;

import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.entities.Player;


public class AttackCommand implements Command {
    private final Player player;
    private final Array<Enemy> enemies;

    public AttackCommand(
        Player player,
        Array<Enemy> enemies
    ) {
        this.player = player;
        this.enemies = enemies;
    }

    @Override
    public void execute(float delta) {
        if (player.attack()) {
            for (int i = 0; i < enemies.size; i++) {
                Enemy enemy = enemies.get(i);
                if (player.getAttackHitbox()
                    .overlaps(enemy.getHitbox())) {
                    enemy.takeDamage(10);
                }
            }
        }
    }
}
