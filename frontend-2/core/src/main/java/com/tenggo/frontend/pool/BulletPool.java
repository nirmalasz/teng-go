package com.tenggo.frontend.pool;

import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.entities.Bullet;

public class BulletPool {
    private final Array<Bullet> bullets;
    public BulletPool(int initialSize) {
        bullets = new Array<>();
        for (int i = 0; i < initialSize; i++) {
            bullets.add(new Bullet());
        }
    }

    public Bullet obtain() {
        for (int i = 0; i < bullets.size; i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.isActive()) {
                return bullet;
            }
        }

        Bullet newBullet = new Bullet();
        bullets.add(newBullet);
        return newBullet;
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }
}
