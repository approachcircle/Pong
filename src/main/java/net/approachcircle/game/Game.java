package net.approachcircle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.backend.Screen;

public class Game extends ApplicationAdapter implements ScreenManager, InputManager {
    public static Game instance;
    private Crosshair crosshair;
    private InputMultiplexer inputMultiplexer;
    private ScreenStack screenStack;

    @Override
    public void create() {
        screenStack = new ScreenStack();
        ScreenUtility.initialise();
        Gdx.input.setInputProcessor(inputMultiplexer);
        screenStack.push(new PongScreen());
        crosshair = new Crosshair();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }
        if (!screenStack.isEmpty()) {
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
}
