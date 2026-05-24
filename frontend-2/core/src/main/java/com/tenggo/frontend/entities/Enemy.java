package com.tenggo.frontend.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private final float renderWidth = 48;
    private final float renderHeight = 48f;

    private int hp = 30;

    private final float speed = 100f;
    private final AttackStrategy attackStrategy;
    private float attackVisualTimer = 0f;

    //later this texture will be moved to constructor and be handled by enemy factory
    private static Texture baseTexture;
    private static Texture attackTexture;
    private boolean isAttacking = false;


    // for now, no enemy factory, will later be implemented to handle this
    public Enemy(float x, float y, AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;

        // Lazy initialization
        if (baseTexture == null) {
            baseTexture = new Texture(Gdx.files.internal("enemy.png"));
        }
        if (attackTexture == null) {
            attackTexture = new Texture(Gdx.files.internal("enemy-attack.png"));
        }

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

//        Vector2 direction =
//            new Vector2(
//                player.getPosition().x - position.x,
//                player.getPosition().y - position.y
//            );
//
//        direction.nor();
        hitbox.setPosition(position.x, position.y);
    }

    public void move(float dx,float dy,Player player,Array<Enemy> enemies) {
        Vector2 separation = new Vector2(0, 0);
        float preferredDistance = 30f;

        for (int i = 0; i < enemies.size; i++) {
            Enemy other = enemies.get(i);
            if (other == this) continue;

            Vector2 diff = new Vector2(position.x - other.getPosition().x, position.y - other.getPosition().y);
            float distance = diff.len();

            if (distance < preferredDistance && distance > 0.01f) {
                diff.nor();

                float pushStrength = (preferredDistance - distance) * 0.1f;
                separation.add(diff.scl(pushStrength));
            }
        }

        float nextX = position.x + dx + separation.x;
        float nextY = position.y + dy + separation.y;

        Rectangle futureHitbox =
            new Rectangle(
                nextX,
                nextY,
                width,
                height
            );

        if (!futureHitbox.overlaps(player.getHitbox())) {
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

    public void render(SpriteBatch batch) {
        Texture currentTexture = isAttacking ? attackTexture : baseTexture;

        float drawX = position.x - (renderWidth - width) / 2f;
        float drawY = position.y - (renderHeight - height) / 2f;

        batch.draw(currentTexture, drawX, drawY, renderWidth, renderHeight);
    }

    public static void disposeTextures() {
        if (baseTexture != null) {
            baseTexture.dispose();
            baseTexture = null;
        }
        if (attackTexture != null) {
            attackTexture.dispose();
            attackTexture = null;
        }
    }

    public void setAttacking(boolean attacking) {
        this.isAttacking = attacking;
        if (attacking) {
            this.attackVisualTimer = 0.5f;
        }
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

    public void setX(float x) {
        position.x = x;
        hitbox.setPosition(position.x, position.y);
    }
    public void setY(float y) {
        position.y = y;
        hitbox.setPosition(position.x, position.y);
    }
}
