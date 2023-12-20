package net.approachcircle.game;

import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.Screen;

public class TestScreen extends Screen {
    Button button;
    public TestScreen() {
        button = new Button("test button", true);
        button.center();
    }

    @Override
    public void render() {
        button.render();
    }
}
