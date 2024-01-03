package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.*;

import java.util.Locale;

public class MainMenuScreen extends Screen implements CloseOnEscape {
    private final TextRenderable title;
    private final Button singleplayerButton;
    private final Button multiplayerButton;
    private final int title_padding = 50;
    private final int sp_button_padding = 50;
    private final int mp_button_padding = 25;
    private final DialogBox comingSoon;
    private final DialogBox wrongLayout;
    private final StringBuilder wrongLayoutMessage = new StringBuilder()
            .append("it looks like you may not be using a UK QWERTY keyboard layout!\n")
            .append("please note that your layout may not be fully supported\n")
            .append("on text entry boxes, and you may encounter issues with\n")
            .append("punctuation. i apologise for the inconvenience.");

    public MainMenuScreen() {
        comingSoon = new DialogBox(DialogType.Information, "coming soon!", Game.getInstance());
        wrongLayout = new DialogBox(DialogType.Information, wrongLayoutMessage.toString(), Game.getInstance());
        title = new TextRenderable("Pong", DefaultTextScaling.TITLE);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - title_padding);
        singleplayerButton = new Button("Singleplayer", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new DifficultySelectScreen());
        }, Game.getInstance());
        singleplayerButton.center();
        singleplayerButton.setY(singleplayerButton.getY() + sp_button_padding);
        multiplayerButton = new Button("Multiplayer", true, (x, y, b) -> {
            comingSoon.toggle();
        }, Game.getInstance());
        multiplayerButton.center();
        multiplayerButton.setY(multiplayerButton.getY() - (sp_button_padding + mp_button_padding));
        if (!Locale.getDefault().getCountry().equalsIgnoreCase("GB")) {
            wrongLayout.toggle();
        }
    }
    @Override
    public void render() {
        title.render();
        singleplayerButton.render();
        multiplayerButton.render();
        comingSoon.render();
        wrongLayout.render();
    }
}
