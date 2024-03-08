package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.network.GameEvent;
import net.approachcircle.game.network.ServerConnection;
import net.approachcircle.game.network.ServerResponse;
import net.approachcircle.game.network.State;

public class JoinGameScreen extends Screen {
    private final TextEntry gameCode;

    public JoinGameScreen() {
        gameCode = new TextEntry(6, Game.getInstance());
        gameCode.setWidthAtLeastByChars(6);
        gameCode.center();
        addMember(gameCode);
        TextRenderable title = new TextRenderable("Join a game", DefaultTextScaling.TITLE);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - 50);
        addMember(title);
        Button joinButton = new Button("Join", true, (x, y, b) -> {
            ServerResponse response = ServerConnection.getInstance().emitEventSynchronously(GameEvent.JOIN_GAME, gameCode.getText());
            if (response.state.equals(State.Error)) {
                Game.getInstance().getNotificationGroup().add(new ErrorNotification("an error occurred"));
            } else if (response.state.equals(State.OK)) {
                Game.getInstance().getNotificationGroup().add(new DialogBox(DialogType.Information, "you're in!", Game.getInstance()));
            } else {
                throw new EnumConstantNotPresentException(State.class, response.state.name());
            }
        }, Game.getInstance());
        addMember(joinButton);
    }
    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Pop;
    }
}
