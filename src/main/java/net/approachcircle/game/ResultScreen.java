package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.TextRenderable;

import java.util.Objects;

public class ResultScreen extends Screen {
    private final Outcome outcome;
    private final TextRenderable text;
    private final int text_padding = 50;
    private final int retry_button_padding = 50;
    private final int mm_button_padding = 25;
    private final Button retryButton;
    private final Button mainMenuButton;
    public ResultScreen(Outcome outcome) {
        this.outcome = outcome;
        text = new TextRenderable("You " + outcome.name().toLowerCase() + "!", 1.5f);
        text.centerX();
        text.setY(Gdx.graphics.getHeight() - text_padding);
        retryButton = new Button("Retry", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().pop();
        }, Game.getInstance());
        retryButton.center();
        retryButton.setY(retryButton.getY() + retry_button_padding);
        mainMenuButton = new Button("Main menu", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().pop();
            Game.getInstance().getScreenStack().pop();
        }, Game.getInstance());
        mainMenuButton.center();
        mainMenuButton.setY(mainMenuButton.getY() - (retry_button_padding + mm_button_padding));
    }

    @Override
    public void render() {
        text.render();
        retryButton.render();
        mainMenuButton.render();
    }
}

