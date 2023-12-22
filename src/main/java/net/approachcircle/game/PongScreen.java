package net.approachcircle.game;

import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.ScreenStack;


public class PongScreen extends Screen {
    private final Player player;
    private final Opponent opponent;
    private final Ball ball;
    private final Score score;
    private final ScreenStack screenStack;

    public PongScreen(Difficulty difficulty) {
        this.screenStack = Game.getInstance().getScreenStack();
        player = Player.getInstance();
        opponent = Opponent.getInstance();
        opponent.setDifficulty(difficulty);
        ball = Ball.getInstance();
        score = Score.getInstance();
    }

    @Override
    public void render() {
        player.render();
        opponent.render();
        ball.render();
        score.render();
        if (score.playerWon()) {
            screenStack.push(new ResultScreen(Outcome.Win));
        }
        if (score.opponentWon()) {
            screenStack.push(new ResultScreen(Outcome.Lose));
        }
        if (gameFinished()) {
            ball.center();
            player.centerY();
            opponent.centerY();
            score.reset();
        }
    }

    private boolean gameFinished() {
        return score.playerWon() || score.opponentWon();
    }
}
