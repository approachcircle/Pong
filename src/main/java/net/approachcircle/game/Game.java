package net.approachcircle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.backend.Screen;

public class Game extends ApplicationAdapter implements ScreenManager, InputManager {
    private static Screen currentScreen;
    private boolean clearOnRender = true;
    public static Game instance;
    private Crosshair crosshair;
    private InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        ScreenUtility.initialise();
        Gdx.input.setInputProcessor(inputMultiplexer);
        setCurrentScreen(new PongScreen());
        crosshair = new Crosshair();
    }

    @Override
    public void render() {
        if (clearOnRender) {
            ScreenUtils.clear(Color.BLACK);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }
        currentScreen.render();
        crosshair.render();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    @Override
    public Screen getCurrentScreen() {
        return currentScreen;
    }

    @Override
    public void setCurrentScreen(Screen newScreen, boolean clearOnRender) {
        this.clearOnRender = clearOnRender;
        currentScreen = newScreen;
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
}
