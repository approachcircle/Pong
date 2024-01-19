package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.network.ConnectionState;
import net.approachcircle.game.network.ServerConnection;

import java.util.Locale;

public class MainMenuScreen extends Screen {
    private final TextRenderable title;
    private final TextRenderable connectionState;
    private final float connectionStatePadding = 15;
    private final TransformableCircle connectionStateIcon;
    private final float connectionStateIconOffset = 25;
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
        connectionState = new TextRenderable(DefaultTextScaling.SMALL);
        connectionState.setText(String.valueOf(ServerConnection.getInstance().getState()));
        connectionState.setX(Gdx.graphics.getWidth() - (connectionState.getWidth() + connectionStatePadding));
        connectionState.setY(connectionState.getHeight() + connectionStatePadding);
        connectionStateIcon = new TransformableCircle();
        connectionStateIcon.setX(connectionState.getX() - (connectionStateIconOffset + (connectionStateIcon.getWidth() / 2)));
        connectionStateIcon.setY(connectionState.getY() - connectionState.getHeight() / 2);
        singleplayerButton = new Button("Singleplayer", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new DifficultySelectScreen());
        }, Game.getInstance());
        singleplayerButton.center();
        singleplayerButton.setY(singleplayerButton.getY() + sp_button_padding);
        multiplayerButton = new Button("Multiplayer", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new MultiplayerCreationScreen());
        }, Game.getInstance());
        multiplayerButton.center();
        multiplayerButton.setY(multiplayerButton.getY() - (sp_button_padding + mp_button_padding));
        if (!Locale.getDefault().getCountry().equalsIgnoreCase("GB")) {
            wrongLayout.toggle();
        }
    }

    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Exit;
    }

    @Override
    public void render() {
        title.render();
        connectionState.setText(String.valueOf(ServerConnection.getInstance().getState()));
        connectionState.render();
        if (ServerConnection.getInstance().getState() == ConnectionState.Online) {
            connectionStateIcon.setColor(Color.GREEN);
        } else {
            connectionStateIcon.setColor(Color.GRAY);
        }
        connectionStateIcon.render();
        singleplayerButton.render();
        multiplayerButton.render();
        comingSoon.render();
        wrongLayout.render();
    }
}
