package net.approachcircle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import net.approachcircle.game.backend.*;

public class Game extends ApplicationAdapter implements ScreenManager, InputManager {
    public static Game instance;
    private Crosshair crosshair;
    private InputMultiplexer inputMultiplexer;
    private ScreenStack screenStack;
    private boolean suspended = false;

    @Override
    public void create() {
        inputMultiplexer = new InputMultiplexer();
        screenStack = new ScreenStack(this);
        ScreenUtility.initialise();
        Gdx.input.setInputProcessor(inputMultiplexer);
        screenStack.push(new MainMenuScreen());
        crosshair = new Crosshair();
    }

    @Override
    public void render() {
        if (!suspended) {
            ScreenUtils.clear(Color.BLACK);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && getScreenStack().peek() instanceof CloseOnEscape) {
            Gdx.app.exit();
        }
        if (!screenStack.isEmpty() && !suspended) {
            screenStack.peek().render();
        }
        crosshair.render();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    @Override
    public ScreenStack getScreenStack() {
        return screenStack;
    }

    @Override
    public void addInputProcessor(InputProcessor processor) {
        if (inputMultiplexer == null) {
            inputMultiplexer = new InputMultiplexer();
        }
        inputMultiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void removeInputProcessor(InputProcessor processor) {
        inputMultiplexer.removeProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void clearInputProcessors() {
        inputMultiplexer.clear();
    }

    public void toggleSuspend() {
        suspended = !suspended;
    }
}
