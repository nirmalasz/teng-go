package com.tenggo.frontend.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    private final Vector2 position;
    private final Vector2 direction;
    private final Rectangle hitbox;
    private boolean active = false;
    private final float speed = 300f;
    private final int size = 8;

    public Bullet() {
        position = new Vector2();
        direction = new Vector2();
        hitbox = new Rectangle();
    }

    public void activate(
        float x,
        float y,
        Vector2 dir
    ) {

        active = true;
        position.set(x, y);
        direction.set(dir).nor();
        hitbox.set(x, y, size, size);
    }

    public void update(float delta) {

        if (!active) return;
        position.x += direction.x * speed * delta;
        position.y += direction.y * speed * delta;

        hitbox.setPosition(position.x, position.y);

        // offscreen
        if (
            position.x < 0 ||
                position.x > 800 ||
                position.y < 0 ||
                position.y > 600
        ) {
            deactivate();
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;
        shapeRenderer.rect(
            position.x,
            position.y,
            size,
            size
        );
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
