package net.approachcircle.game;

import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.TextRenderable;

public class VictoryScreen extends Screen {
    private final TextRenderable text;
    public VictoryScreen() {
        text = new TextRenderable("you win", 1);
        text.center();
    }

    @Override
    public void render() {
        text.render();
    }
}
