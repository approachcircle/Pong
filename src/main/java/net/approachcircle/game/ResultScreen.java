package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.DefaultTextScaling;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.TextRenderable;

public class ResultScreen extends Screen {
    private final int text_padding = 50;
    private final int retry_button_padding = 50;
    private final int mm_button_padding = 25;

    public ResultScreen(Outcome outcome, Difficulty lastDifficulty) {
        TextRenderable text = new TextRenderable("You " + outcome.name().toLowerCase() + "!", DefaultTextScaling.TITLE);
        text.centerX();
        text.setY(Gdx.graphics.getHeight() - text_padding);
        addMember(text);
        Button retryButton = new Button("Retry", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().popTo(DifficultySelectScreen.class);
            Game.getInstance().getScreenStack().push(new PongScreen(lastDifficulty));
        }, Game.getInstance());
        retryButton.center();
        retryButton.setY(retryButton.getY() + retry_button_padding);
        addMember(retryButton);
        Button mainMenuButton = new Button("Main menu", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().popTo(MainMenuScreen.class);
        }, Game.getInstance());
        mainMenuButton.center();
        mainMenuButton.setY(mainMenuButton.getY() - (retry_button_padding + mm_button_padding));
        addMember(mainMenuButton);
    }
}

