package com.tenggo.frontend.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {

    private final Command moveUp;
    private final Command moveDown;
    private final Command moveLeft;
    private final Command moveRight;
    private final Command attack;
    private final Command dash;

    public InputHandler(
        Command moveUp,
        Command moveDown,
        Command moveLeft,
        Command moveRight,
        Command attack,
        Command dash
    ) {

        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.attack = attack;
        this.dash = dash;
    }

    public void handleInput(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp.execute(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown.execute(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft.execute(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight.execute(delta);
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            attack.execute(delta);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            dash.execute(delta);
        }
    }
}
