package net.approachcircle.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setMaximized(true);
        config.setTitle("Pong");
        config.setWindowIcon(Files.FileType.Classpath, "app-icon.png");
        try {
            new Lwjgl3Application(Game.getInstance(), config);
        } catch (RuntimeException e) {
            UserFriendlyCrashHandler.handle(e);
            e.printStackTrace();
            Gdx.app.exit();
        }
    }
}
