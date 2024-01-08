package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.*;

public class MultiplayerCreationScreen extends Screen {
    private final Button joinButton;
    private final Button createButton;
    private final int PADDING = 50;
    private final TextRenderable title;
    private final DialogBox offlineDialog;
    private final DialogBox notImplementedDialog;

    public MultiplayerCreationScreen() {
        notImplementedDialog = new DialogBox(DialogType.Information, "not implemented! come back soon.", Game.getInstance());
        offlineDialog = new DialogBox(DialogType.Information, "you are not online!", new DialogListenerAdapter() {
            @Override
            public void onOk() {
                Game.getInstance().getScreenStack().pop();
            }
        }, Game.getInstance());
        if (ServerConnection.getInstance().getState() == ConnectionState.Offline) {
            offlineDialog.toggle();
        }
        title = new TextRenderable("Game creation");
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - PADDING);
        joinButton = new Button("Join a game", true, (x, y, b) -> {
            notImplementedDialog.toggle();
        }, Game.getInstance());
        joinButton.center();
        joinButton.setX(joinButton.getX() - (joinButton.getWidth() / 2) - PADDING);
        joinButton.setY(PADDING * 3);
        createButton = new Button("Create a game", true, (x, y, b) -> {
            notImplementedDialog.toggle();
        }, Game.getInstance());
        createButton.center();
        createButton.setX(createButton.getX() + (createButton.getWidth() / 2) + PADDING);
        createButton.setY(PADDING * 3);
    }

    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Pop;
    }

    @Override
    public void render() {
        title.render();
        joinButton.render();
        createButton.render();
        offlineDialog.render();
        notImplementedDialog.render();
    }
}
