package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.network.ConnectionState;
import net.approachcircle.game.network.ServerConnection;

import java.util.Locale;

public class MainMenuScreen extends Screen {
    private final TextRenderable connectionState;
    private final float connectionStatePadding = 15;
    private final TransformableCircle connectionStateIcon;
    private final float connectionStateIconOffset = 25;
    private final int title_padding = 50;
    private final int sp_button_padding = 50;
    private final int mp_button_padding = 25;
    private final StringBuilder wrongLayoutMessage = new StringBuilder()
            .append("it looks like you may not be using a UK QWERTY keyboard layout!\n")
            .append("please note that your layout may not be fully supported\n")
            .append("on text entry boxes, and you may encounter issues with\n")
            .append("punctuation. i apologise for the inconvenience.");

    public MainMenuScreen() {
        TextRenderable title = new TextRenderable("Pong", DefaultTextScaling.TITLE);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - title_padding);
        addMember(title);
        connectionState = new TextRenderable(DefaultTextScaling.SMALL);
        connectionState.setText(String.valueOf(ServerConnection.getInstance().getState()));
        connectionState.setX(Gdx.graphics.getWidth() - (connectionState.getWidth() + connectionStatePadding));
        connectionState.setY(connectionState.getHeight() + connectionStatePadding);
        addMember(connectionState);
        connectionStateIcon = new TransformableCircle();
        connectionStateIcon.setX(connectionState.getX() - (connectionStateIconOffset + (connectionStateIcon.getWidth() / 2)));
        connectionStateIcon.setY(connectionState.getY() - connectionState.getHeight() / 2);
        addMember(connectionStateIcon);
        Button singleplayerButton = new Button("Singleplayer", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new DifficultySelectScreen());
        }, Game.getInstance());
        singleplayerButton.center();
        singleplayerButton.setY(singleplayerButton.getY() + sp_button_padding);
        addMember(singleplayerButton);
        Button multiplayerButton = new Button("Multiplayer", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new MultiplayerCreationScreen());
        }, Game.getInstance());
        multiplayerButton.center();
        multiplayerButton.setY(multiplayerButton.getY() - (sp_button_padding + mp_button_padding));
        addMember(multiplayerButton);
        if (!Locale.getDefault().getCountry().equalsIgnoreCase("GB")) {
            // wrongLayout.toggleVisibility();
            Game.getInstance().getNotificationStack().push(
                    new DialogBox(DialogType.Information, wrongLayoutMessage.toString(), Game.getInstance())
            );
        }
    }

    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Exit;
    }

    @Override
    public void update() {
        connectionState.setText(String.valueOf(ServerConnection.getInstance().getState()));
        if (ServerConnection.getInstance().getState() == ConnectionState.Online) {
            connectionStateIcon.setColor(Color.GREEN);
        } else {
            connectionStateIcon.setColor(Color.GRAY);
        }
    }
}
