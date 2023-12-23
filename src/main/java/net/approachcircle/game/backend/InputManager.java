package net.approachcircle.game.backend;

import com.badlogic.gdx.InputProcessor;

public interface InputManager {
    void addInputProcessor(InputProcessor processor);
    void removeInputProcessor(InputProcessor processor);
    void clearInputProcessors();
}
