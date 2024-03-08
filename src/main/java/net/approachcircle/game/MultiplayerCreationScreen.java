package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.network.ConnectionState;
import net.approachcircle.game.network.GameEvent;
import net.approachcircle.game.network.ServerConnection;
import net.approachcircle.game.network.ServerResponse;

public class MultiplayerCreationScreen extends Screen {
    private final int PADDING = 50;

    public MultiplayerCreationScreen() {
        TextRenderable title = new TextRenderable("Game creation");
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - PADDING);
        addMember(title);
        Button joinButton = new Button("Join a game", true, (x, y, b) -> {
            Game.getInstance().getScreenStack().push(new JoinGameScreen());
        }, Game.getInstance());
        joinButton.center();
        joinButton.setX(joinButton.getX() - (joinButton.getWidth() / 2) - PADDING);
        joinButton.setY(PADDING * 3);
        addMember(joinButton);
        Button createButton = new Button("Create a game", true, (x, y, b) -> {
            ServerResponse response = ServerConnection.getInstance().emitEventSynchronously(GameEvent.CREATE_GAME);
        }, Game.getInstance());
        createButton.center();
        createButton.setX(createButton.getX() + (createButton.getWidth() / 2) + PADDING);
        createButton.setY(PADDING * 3);
        addMember(createButton);
        if (ServerConnection.getInstance().getState() == ConnectionState.Offline) {
            joinButton.setState(ButtonState.Disabled);
            createButton.setState(ButtonState.Disabled);
            Game.getInstance().getNotificationGroup().add(new ErrorNotification("you are not online!", new NotificationListenerAdapter() {
                @Override
                public void onExit() {
                    if (Game.getInstance().getScreenStack().peek() instanceof MultiplayerCreationScreen) {
                        Game.getInstance().getScreenStack().pop();
                    }
                }
            }));
        }
    }

    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Pop;
    }
}
