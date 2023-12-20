package net.approachcircle.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import net.approachcircle.game.backend.Crosshair;
import net.approachcircle.game.backend.IScreenManager;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.ScreenUtility;

public class Game extends ApplicationAdapter implements IScreenManager {
    private static Screen currentScreen;
    private boolean clearOnRender = true;
    public static Game instance;
    private Crosshair crosshair;

    @Override
    public void create() {
        ScreenUtility.initialise();
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
}
