package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.network.GameEvent;
import net.approachcircle.game.network.ServerConnection;
import net.approachcircle.game.network.ServerResponse;
import net.approachcircle.game.network.State;

public class JoinGameScreen extends Screen {
    private final TextEntry gameCode;
    private final DialogBox errorDialog;

    public JoinGameScreen() {
        errorDialog = new DialogBox(DialogType.Information, "an error occurred", Game.getInstance());
        gameCode = new TextEntry(6, Game.getInstance());
        gameCode.setWidthAtLeastByChars(6);
        gameCode.center();
        addMember(gameCode);
        TextRenderable title = new TextRenderable("Join a game", DefaultTextScaling.TITLE);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - 50);
        addMember(title);
        Button joinButton = new Button("Join", true, (x, y, b) -> {
            errorDialog.toggle();
            ServerResponse response = ServerConnection.getInstance().emitEventSynchronously(GameEvent.JOIN_GAME, gameCode.getText());
            if (response.state.equals(State.Error)) {
                errorDialog.setPrompt(response.message);
            } else if (response.state.equals(State.OK)) {
                errorDialog.setPrompt("you're in!");
            } else {
                throw new EnumConstantNotPresentException(State.class, response.state.name());
            }
        }, Game.getInstance());
        addMember(joinButton);
        addMember(errorDialog);
    }
    @Override
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Pop;
    }
}
