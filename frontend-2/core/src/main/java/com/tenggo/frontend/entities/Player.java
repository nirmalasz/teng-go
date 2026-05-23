package com.tenggo.frontend.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tenggo.frontend.observer.Observer;
import com.tenggo.frontend.observer.Subject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    private int maxDashCount = 1;
    private int currentDashCount = 1;
    private float dashDistance = 100f;
    private float dashCooldown = 0f;

    private final float maxDashCooldown = 1f;

    private final Vector2 facingDirection = new Vector2(1, 0);

    private int deathDefianceCount = 0;
    private boolean invulnerable = false;
    private float invulnerableTimer = 0f;

    private final Rectangle attackHitbox;

    private final Array<Observer> observers;

    private Animation<TextureRegion>[] runAnimations;
    private Animation<TextureRegion>[] attackAnimations;
    private float stateTime;

    public int currentDirection = 0;
    public boolean isAttacking = false;
    public boolean isFacingLeft = false;

    private final float DRAW_SIZE=48f;

    public Player(float x, float y) {
        position = new Vector2(x, y);
        hitbox = new Rectangle(
            x,
            y,
            DRAW_SIZE,
            DRAW_SIZE
        );
        attackHitbox = new Rectangle();
        observers = new Array<>();
        this.position.set(x, y);
        loadAnimations();
    }

    @SuppressWarnings("unchecked")
    private void loadAnimations() {
        Texture runSheet = new Texture("16x16 Run-Sheet.png");
        Texture attackSheet = new Texture("16x16 Attack-Sheet.png");

        int frameWidth = runSheet.getWidth() / 6;   // 6 columns
        int frameHeight = runSheet.getHeight() / 5; // 5 rows

        TextureRegion[][] runTmp = TextureRegion.split(runSheet, frameWidth, frameHeight);
        TextureRegion[][] attackTmp = TextureRegion.split(attackSheet, frameWidth, frameHeight);

        runAnimations = (Animation<TextureRegion>[]) new Animation[5];
        attackAnimations = (Animation<TextureRegion>[]) new Animation[5];

        for (int row = 0; row < 5; row++) {
            TextureRegion[] rFrames = new TextureRegion[6];
            TextureRegion[] aFrames = new TextureRegion[6];

            for (int col = 0; col < 6; col++) {
                rFrames[col] = runTmp[row][col];
                aFrames[col] = attackTmp[row][col];
            }

            runAnimations[row] = new Animation<TextureRegion>(0.1f, new Array<>(rFrames), Animation.PlayMode.LOOP);
            attackAnimations[row] = new Animation<TextureRegion>(0.1f, new Array<>(aFrames), Animation.PlayMode.NORMAL);
        }

        stateTime = 0f;
    }

    public void update(float delta) {
        if (invulnerable) {
            invulnerableTimer -= delta;
            if (invulnerableTimer <= 0) {
                invulnerable = false;
            }
        }
        stateTime += delta;

        if (damageCooldown > 0) {
            damageCooldown -= delta;
        }

        if (attackCooldown > 0) {
            attackCooldown -= delta;
        }

        if (dashCooldown > 0) {
            dashCooldown -= delta;
        }else {
            currentDashCount = maxDashCount;
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

        if (dx != 0 || dy != 0) {
            facingDirection.set(dx, dy).nor();
            if (dx < 0) {
                isFacingLeft = true;
            } else if (dx > 0) {
                isFacingLeft = false;
            }

            if (dy > 0) {
                if (dx == 0) currentDirection = 4;
                else currentDirection = 3;
            } else if (dy < 0) {
                if (dx == 0) currentDirection = 0;
                else currentDirection = 1;
            } else {
                if (dx != 0) currentDirection = 2;
            }
        }

        hitbox.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;
        if (isAttacking) {
            currentFrame = attackAnimations[currentDirection].getKeyFrame(stateTime, false);
            if (attackAnimations[currentDirection].isAnimationFinished(stateTime)) {
                isAttacking = false;
            }
        } else {
            currentFrame = runAnimations[currentDirection].getKeyFrame(stateTime, true);
        }
        if (currentFrame.isFlipX() != isFacingLeft) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, position.x, position.y, DRAW_SIZE, DRAW_SIZE);
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
        isAttacking = true;
        stateTime = 0f;
        attackHitbox.set(
            position.x - 8,
            position.y - 8,
            width + 16,
            height + 16
        );

        return true;
    }

    public void takeDamage(int damage) {
        if (invulnerable) {
            return;
        }
        if (damageCooldown > 0) {
            return;
        }
        hp -= damage;
        damageCooldown = maxDamageCooldown;
        notifyObservers();
    }

    public void dash() {
        if (currentDashCount <= 0) {
            return;
        }

        position.x += facingDirection.x * dashDistance;

        position.y += facingDirection.y * dashDistance;

        // bounds
        if (position.x < 0) position.x = 0;
        if (position.y < 0) position.y = 0;

        if (position.x > 800 - width)
            position.x = 800 - width;
        if (position.y > 600 - height)
            position.y = 600 - height;

        hitbox.setPosition(position.x, position.y);

        currentDashCount--;
        dashCooldown = maxDashCooldown;
    }

    public boolean isDead() {
        if (hp <= 0) {

            if (deathDefianceCount > 0) {
                deathDefianceCount--;
                hp = 50;
                invulnerable = true;
                invulnerableTimer = 1f;
                return false;
            }
            return true;
        }
        return false;
    }

    public void setDeathDefianceCount(int count) {
        deathDefianceCount = count;
    }

    public void setDashCount(int count){
        maxDashCount = count;
        currentDashCount = count;
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

    public void increaseDashCount() {
        maxDashCount++;
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

    public void setX(float v) {
        position.x = v;
        hitbox.setPosition(position.x, position.y);
    }
    public void setY(float v) {
        position.y = v;
        hitbox.setPosition(position.x, position.y);
    }
}
