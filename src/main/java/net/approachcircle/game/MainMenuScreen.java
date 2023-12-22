package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.Button;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.TextRenderable;

public class MainMenuScreen extends Screen {
    private final TextRenderable title;
    private final Button singleplayerButton;
    private final Button multiplayerButton;
    private final int title_padding = 50;
    private final int sp_button_padding = 50;
    private final int mp_button_padding = 25;

    public MainMenuScreen() {
        title = new TextRenderable("Pong", 1.5f);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - title_padding);
        singleplayerButton = new Button("Singleplayer", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new PongScreen());
        }, Game.getInstance());
        singleplayerButton.center();
        singleplayerButton.setY(singleplayerButton.getY() + sp_button_padding);
        multiplayerButton = new Button("Multiplayer", true);
        multiplayerButton.center();
        multiplayerButton.setY(multiplayerButton.getY() - (sp_button_padding + mp_button_padding));
    }
    @Override
    public void render() {
        title.render();
        singleplayerButton.render();
        multiplayerButton.render();
    }
}
