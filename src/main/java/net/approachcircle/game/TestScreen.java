package net.approachcircle.game;

import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.Screen;

public class TestScreen extends Screen {
    private final Button button;
    private final Button button2;
    private int timer = 0;
    public TestScreen() {
        button = new Button("test button", true, (x, y, button) -> {
            System.out.println("button 1 clicked");
        });
        button2 = new Button("test button 2", true, (x, y, button) -> {
            System.out.println("button 2 clicked");
        });
        button.center();
        button2.setX(button.getX() + 100);
        button2.setY(button.getY() + 100);
    }

    @Override
    public void render() {
        if (timer == 3 * 60) {
            button.setX(button2.getX() + 100);
            button.setY(button2.getY() + 100);
        }
        timer++;
        button.render();
        button2.render();
    }
}
