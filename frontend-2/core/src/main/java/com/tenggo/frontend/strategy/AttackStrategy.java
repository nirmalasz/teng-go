package com.tenggo.frontend.strategy;

import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Enemy;
import com.tenggo.frontend.entities.Player;

public interface AttackStrategy {
    void attack(
        Enemy enemy,
        Player player,
        Array<Enemy> enemies,
        float delta
    );
}
