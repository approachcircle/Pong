package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import net.approachcircle.game.backend.CollisionUtility;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.ScreenStack;
import net.approachcircle.game.backend.Size;

import java.util.Random;


public class PongScreen extends Screen {
    private final Player player;
    private final Opponent opponent;
    private final Ball ball;
    private final Score score;
    private final ScreenStack screenStack;
    private final Difficulty difficulty;

    public PongScreen(Difficulty difficulty) {
        this.screenStack = Game.getInstance().getScreenStack();
        player = new Player();
        opponent = new Opponent();
        opponent.setDifficulty(difficulty);
        ball = new Ball();
        chooseRandomStartTrajectory();
        switch (difficulty) {
            case Easy -> ball.speedIncline += 0;
            case Normal -> ball.speedIncline += 1;
            case Hard -> ball.speedIncline += 1.25f;
            case Extreme -> ball.speedIncline += 2.25f;
            case Impossible -> ball.speedIncline += 4;
            default -> throw new EnumConstantNotPresentException(Difficulty.class, difficulty.name());
        }
        this.difficulty = difficulty;
        score = new Score();
    }


    private void handleCollisionProjection() {
        if (CollisionUtility.isCollidingWith(
                new Vector2(player.getX(), player.getY()),
                new Size(player.getWidth(), player.getHeight()),
                new Vector2(ball.getX() + (ball.getDeltaX()) * ball.getSpeed(), ball.getY() + (ball.getDeltaX()) * ball.getSpeed()),
                new Size(ball.getWidth(), ball.getHeight()))) {
            while (ball.getX() < player.getX() + player.getWidth()) {
                ball.setX(ball.getX() + 1);
            }
            ball.setDeltaX(-ball.getDeltaX());
            ball.setSpeed(ball.getSpeed() + ball.speedIncline);
            return;
        }
        if (CollisionUtility.isCollidingWith(
                new Vector2(opponent.getX(), opponent.getY()),
                new Size(opponent.getWidth(), opponent.getHeight()),
                new Vector2(ball.getX() + (ball.getDeltaX()) * ball.getSpeed(), ball.getY() + (ball.getDeltaX()) * ball.getSpeed()),
                new Size(ball.getWidth(), ball.getHeight()))) {
            ball.setDeltaX(-ball.getDeltaX());
            ball.setSpeed(ball.getSpeed() + ball.speedIncline);
        }
    }

    public void handleCollisions() {
        if (CollisionUtility.isCollidingWithWall(ball, Wall.Left)) {
            score.incrementOpponent();
            ball.center();
            ball.resetSpeed();
        }
        if (CollisionUtility.isCollidingWithWall(ball, Wall.Right)) {
            score.incrementPlayer();
            ball.center();
            ball.resetSpeed();
        }
        if (CollisionUtility.isCollidingWithWall(ball, Wall.Top)) {
            while (ball.getY() > Gdx.graphics.getHeight()) {
                ball.setY(ball.getY() - 1);
            }
            ball.setDeltaY(-ball.getDeltaY());
        }
        if (CollisionUtility.isCollidingWithWall(ball, Wall.Bottom)) {
            while (ball.getY() < 0) {
                ball.setY(ball.getY() + 1);
            }
            ball.setDeltaY(-ball.getDeltaY());
        }
    }

    @Override
    public void render() {
        handleCollisionProjection();
        handleCollisions();
        moveAIOpponent();
        player.render();
        opponent.render();
        ball.render();
        score.render();
        if (score.playerWon()) {
            screenStack.push(new ResultScreen(Outcome.Win, difficulty));
        }
        if (score.opponentWon()) {
            screenStack.push(new ResultScreen(Outcome.Lose, difficulty));
        }
//        if (gameFinished()) {
//            ball.center();
//            player.centerY();
//            opponent.centerY();
//            score.reset();
//            ball.resetSpeed();
//        }
    }

    private boolean gameFinished() {
        return score.playerWon() || score.opponentWon();
    }

    private void moveAIOpponent() {
        if (opponent.getY() > ball.getY() - (opponent.getHeight() / 2) && opponent.getY() > 0) {
            opponent.setY(opponent.getY() - opponent.getSpeed());
        }
        if (opponent.getY() < ball.getY() - (opponent.getHeight() / 2) && opponent.getY() <= Gdx.graphics.getHeight() - opponent.getHeight()) {
            opponent.setY(opponent.getY() + opponent.getSpeed());
        }
    }

    private void chooseRandomStartTrajectory() {
        Random rng = new Random();
        int result = rng.nextInt(2);
        if (result == 1) {
            ball.setDeltaX(-ball.getDeltaX());
        }
        result = rng.nextInt(2);
        if (result == 1) {
            ball.setDeltaY(-ball.getDeltaY());
        }
    }
}
