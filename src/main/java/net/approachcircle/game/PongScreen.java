package net.approachcircle.game;

import net.approachcircle.game.backend.Screen;


public class PongScreen extends Screen {
    private final Player player;
    private final Opponent opponent;
    private final Ball ball;
    private final Score score;

    public PongScreen() {
        player = Player.getInstance();
        opponent = Opponent.getInstance();
        ball = Ball.getInstance();
        score = Score.getInstance();
    }

    @Override
    public void render() {
        player.render();
        opponent.render();
        ball.render();
        score.render();
    }
}
