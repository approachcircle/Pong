package net.approachcircle.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.approachcircle.game.backend.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.initialise("Pong");
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setMaximized(true);
        config.setTitle("Pong");
        config.setWindowIcon(Files.FileType.Classpath, "app-icon.png");
        try {
            new Lwjgl3Application(Game.getInstance(), config);
        } catch (RuntimeException e) {
            Logger.error("crash caught!");
            UserFriendlyCrashHandler.handle(e);
            Logger.error(String.valueOf(e));
            for (StackTraceElement element : e.getStackTrace()) {
                Logger.error("\t" + element);
            }
            Gdx.app.exit();
        }
    }
}
