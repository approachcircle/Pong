package net.approachcircle.game.backend;

public interface IScreenManager {
    Screen getCurrentScreen();
    void setCurrentScreen(Screen newScreen, boolean clearOnRender);
    default void setCurrentScreen(Screen newScreen) {
        setCurrentScreen(newScreen, true);
    }
}
