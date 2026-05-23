package com.tenggo.frontend.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.observer.Observer;
import com.tenggo.frontend.observer.Subject;

public class Player implements Subject {
    private final Vector2 position;
    private final Rectangle hitbox;


    private final int width = 16;
    private final int height = 16;

    private int hp = 100;
    private float attackCooldown = 0f;

    private int damage = 10;
    private float speed = 200f;

    private float damageCooldown = 0f;
    private final float maxDamageCooldown = 0.5f;

    private final Rectangle attackHitbox;

    private final Array<Observer> observers;

    public Player(float x, float y) {
        position = new Vector2(x, y);
        hitbox = new Rectangle(
            x,
            y,
            width,
            height
        );
        attackHitbox = new Rectangle();
        observers = new Array<>();
    }

    public void update(float delta) {
        if (damageCooldown > 0) {
            damageCooldown -= delta;
        }

        if (attackCooldown > 0) {
            attackCooldown -= delta;
        }
    }

    public void move(float dx, float dy) {
        position.x += dx;
        position.y += dy;

        // screen bounds
        if (position.x < 0) position.x = 0;
        if (position.y < 0) position.y = 0;

        if (position.x > 800 - width)
            position.x = 800 - width;

        if (position.y > 600 - height)
            position.y = 600 - height;

        hitbox.setPosition(position.x, position.y);
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

    public Vector2 getPosition() {
        return position;
    }

    public boolean attack() {
        if (attackCooldown > 0) {
            return false;
        }
        attackCooldown = 0.3f;
        attackHitbox.set(
            position.x - 8,
            position.y - 8,
            width + 16,
            height + 16
        );

        return true;
    }

    public void takeDamage(int damage) {
        if (damageCooldown > 0) {
            return;
        }
        hp -= damage;
        damageCooldown = maxDamageCooldown;
        notifyObservers();
    }
    public boolean isDead() {
        return hp <= 0;
    }
    public int getHp() {
        return hp;
    }
    public Rectangle getAttackHitbox() {
        return attackHitbox;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void increaseMaxHp(int amount) {
        hp += amount;
    }

    public void increaseDamage(int amount) {
        damage += amount;
    }

    public void increaseSpeed(float amount) {
        speed += amount;
    }

    // subject methods
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.removeValue(observer, true);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size; i++) {
            observers.get(i).update(hp);
        }
    }
}
