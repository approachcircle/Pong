package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import net.approachcircle.game.backend.*;

import java.util.Random;


public class PongScreen extends Screen {
    private final Player player;
    private final Opponent opponent;
    private final Ball ball;
    private final Score score;
    private final Difficulty difficulty;
    private final TextRenderable roundName;

    public PongScreen(Difficulty difficulty) {
        player = new Player();
        addMember(player);
        // TODO: here we should pass an argument to the constructor
        // TODO: of Opponent letting it know if this is a multiplayer
        // TODO: game or a single-player game. exceptions should be thrown
        // TODO: if methods are called in the wrong game mode context
        opponent = new Opponent();
        opponent.setDifficulty(difficulty);
        addMember(opponent);
        ball = new Ball();
        addMember(ball);
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
        addMember(score);
        roundName = new TextRenderable("Round 1", DefaultTextScaling.SMALL);
        roundName.render();
        roundName.centerX(true);
        roundName.setY(Gdx.graphics.getHeight() - roundName.getHeight() - score.getHeight() - 30);
        addMember(roundName);
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
    public void update() {
        handleCollisionProjection();
        handleCollisions();
        moveAIOpponent();
        roundName.setText("Round " + ((score.getOpponentScore() + score.getPlayerScore()) + 1));
        if (score.getPlayerScore() == score.getWinCondition() - 1 ||
                score.getOpponentScore() == score.getWinCondition() - 1) {
            roundName.setText("Match point");
        }
        if (score.getPlayerScore() == score.getWinCondition() - 1 &&
                score.getOpponentScore() == score.getWinCondition() - 1) {
            roundName.setText("Sudden death");
        }
        if (score.playerWon()) {
            Game.getInstance().getScreenStack().push(new ResultScreen(Outcome.Win, difficulty));
        }
        if (score.opponentWon()) {
            Game.getInstance().getScreenStack().push(new ResultScreen(Outcome.Lose, difficulty));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.getInstance().getScreenStack().push(new PauseScreen());
        }
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
