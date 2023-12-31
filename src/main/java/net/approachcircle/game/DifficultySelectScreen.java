package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.*;

import java.util.ArrayList;


public class DifficultySelectScreen extends Screen {
    private final TextRenderable title;
    private final ArrayList<Button> difficultyButtons;
    private final int title_padding = 50;
    private final int further_padding = 15;

    public DifficultySelectScreen() {
        title = new TextRenderable("Select difficulty:", DefaultTextScaling.TITLE);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - title_padding);
        difficultyButtons = new ArrayList<>();
        for (Difficulty diff : Difficulty.values()) {
            Button button = new Button(diff.name(), true, (x, y, b) -> {
                Game.getInstance().getScreenStack().push(new PongScreen(diff));
            }, Game.getInstance());
            button.centerX();
            if (difficultyButtons.isEmpty()) {
                button.setY((title.getY() - title.getHeight()) - button.getHeight() - title_padding);
            } else {
                Button previous = difficultyButtons.get(difficultyButtons.size() - 1);
                button.setY(previous.getY() - (further_padding + previous.getHeight()));
            }
            difficultyButtons.add(button);
        }
    }

    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Pop;
    }

    @Override
    public void render() {
        title.render();
        for (Button button : difficultyButtons) {
            button.render();
        }
    }
}
