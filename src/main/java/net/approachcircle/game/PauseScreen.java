package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.approachcircle.game.backend.*;

public class PauseScreen extends Screen {
    public PauseScreen() {
        Button resumeButton = new Button("Resume", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().pop();
        }, Game.getInstance());
        addMember(resumeButton);
        Button quitButton = new Button("Main menu", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().popTo(MainMenuScreen.class);
        }, Game.getInstance());
        quitButton.setX(Gdx.graphics.getWidth() - quitButton.getWidth());
        addMember(quitButton);
        TextRenderable title = new TextRenderable("Paused", DefaultTextScaling.TITLE);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - 50);
        addMember(title);
    }

    @Override
    public void update() {
        // because of key debounce and the way that escape behaviours are processed, we need to explicitly
        // call pop on the ScreenStack and check if escape is pressed ourselves.
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.getInstance().getScreenStack().pop();
        }
    }
}
