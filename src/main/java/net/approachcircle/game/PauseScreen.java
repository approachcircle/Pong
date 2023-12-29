package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.TextRenderable;

public class PauseScreen extends Screen {
    private final Button resumeButton;
    private final Button quitButton;
    private final TextRenderable title;

    public PauseScreen() {
        resumeButton = new Button("Resume", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().pop();
        }, Game.getInstance());
        quitButton = new Button("Main menu", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().popTo(MainMenuScreen.class);
        }, Game.getInstance());
        quitButton.setX(Gdx.graphics.getWidth() - quitButton.getWidth());
        title = new TextRenderable("Paused", 1f);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - 50);
    }

    @Override
    public void render() {
        quitButton.render();
        resumeButton.render();
        title.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.getInstance().getScreenStack().pop();
        }
    }
}
