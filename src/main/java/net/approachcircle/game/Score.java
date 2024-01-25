package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.backend.DefaultTextScaling;
import net.approachcircle.game.backend.Renderable;
import net.approachcircle.game.backend.TextRenderable;

public class Score extends Renderable {
    private int playerScore = 0;
    private int opponentScore = 0;
    private final float SCALE = DefaultTextScaling.SUBTITLE;
    // private final float SCALE = 4;
    private final int TOP_PADDING = 75;
    private final TextRenderable renderable;
    private final int WIN_CONDITION = 3;

    public Score() {
        renderable = new TextRenderable(SCALE);
        renderable.centerX(true);
        renderable.setY(Gdx.graphics.getHeight() - TOP_PADDING);
    }

    @Override
    public void render() {
        renderable.setText(String.format("%s : %s", playerScore, opponentScore));
        renderable.render();
    }

    public void incrementPlayer() {
        playerScore++;
    }
    public void incrementOpponent() {
        opponentScore++;
    }
    public boolean playerWon() {
        return playerScore == WIN_CONDITION;
    }

    public boolean opponentWon() {
        return opponentScore == WIN_CONDITION;
    }

    public void reset() {
        opponentScore = 0;
        playerScore = 0;
    }
}
