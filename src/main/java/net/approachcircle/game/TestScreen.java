package net.approachcircle.game;

import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.Screen;

public class TestScreen extends Screen {
    private final Button button;
    public TestScreen() {
        button = new Button("test button", true, (x, y, button) -> {
            System.out.println("button clicked");
        });
        button.center();
    }

    @Override
    public void render() {
        button.render();
    }
}
