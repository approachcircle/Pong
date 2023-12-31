package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setMaximized(true);
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.setTitle("Pong");
        try {
            new Lwjgl3Application(Game.getInstance(), config);
        } catch (RuntimeException e) {
            UserFriendlyCrashHandler.handle(e);
            e.printStackTrace();
            Gdx.app.exit();
        }
    }
}
