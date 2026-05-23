package com.tenggo.frontend.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.strategy.AttackStrategy;

public class Enemy {

    private final Vector2 position;

    private final Rectangle hitbox;

    private final int width = 16;
    private final int height = 16;

    private int hp = 30;

    private final float speed = 100f;
    private final AttackStrategy attackStrategy;

    public Enemy(float x, float y, AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
        position = new Vector2(x, y);
        hitbox = new Rectangle(
            x,
            y,
            width,
            height
        );
    }

    public void update(
        float delta,
        Player player,
        Array<Enemy> enemies
    ) {

        attackStrategy.attack(this, player, enemies, delta);

        Vector2 direction =
            new Vector2(
                player.getPosition().x - position.x,
                player.getPosition().y - position.y
            );

        direction.nor();

        hitbox.setPosition(position.x, position.y);
    }

    public void move(float dx,float dy,Player player,Array<Enemy> enemies) {
        float nextX = position.x + dx;
        float nextY = position.y + dy;

        Rectangle futureHitbox =
            new Rectangle(
                nextX,
                nextY,
                width,
                height
            );

        boolean colliding = false;
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy == this) continue;
            if (futureHitbox.overlaps(enemy.getHitbox())) {
                colliding = true;
                break;
            }
        }

        if (!futureHitbox.overlaps(player.getHitbox())&& !colliding) {
            position.x = nextX;
            position.y = nextY;
        }

        hitbox.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed() {
        return speed;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(
            position.x,
            position.y,
            width,
            height
        );
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
