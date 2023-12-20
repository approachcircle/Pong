package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class ScreenUtility {
    private static Vector2 SCREEN_CENTER;
    private static boolean initialised = false;

    public static void initialise() {
        initialised = true;
        SCREEN_CENTER = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
    }

    public static Vector2 getScreenCenter() {
        if (!initialised) {
            throw new RuntimeException("ScreenUtility not initialised");
        }
        return SCREEN_CENTER;
    }
}