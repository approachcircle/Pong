package net.approachcircle.game;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.ScreenUtility;
import net.approachcircle.game.backend.TextRenderable;

public class DefeatScreen extends Screen {
    private TextRenderable text;
    public DefeatScreen() {
        text = new TextRenderable("you lose", 1);
        text.center();
    }

    @Override
    public void render() {
        text.render();
    }
}

