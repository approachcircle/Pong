package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class KeyCombo {
    private static boolean cooldown = false;
    public static boolean isPressed(ArrayList<Integer> keys) {
        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) &&
                Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) &&
                Gdx.input.isKeyPressed(Input.Keys.C) &&
                !cooldown
        ) {
            cooldown = true;
            return true;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) &&
                !Gdx.input.isKeyPressed(Input.Keys.C)
        ) {
            cooldown = false;
        }
        return false;
    }
}
