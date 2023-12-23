package net.approachcircle.game.backend.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import net.approachcircle.game.Ball;
import net.approachcircle.game.Game;
import net.approachcircle.game.Wall;
import net.approachcircle.game.backend.*;

public class CollisionSpeedTestScreen extends Screen {
    private final TextRenderable title;
    private final TextRenderable algoStatus;
    private final TextRenderable speedText;
    private final int title_padding = 50;
    private final Button increaseSpeed;
    private final Button decreaseSpeed;
    private final Button switchAlgorithm;
    private final Button resetButton;
    private final StaticCollider leftCollider;
    private final StaticCollider rightCollider;
    private final Ball ball;
    private boolean oldAlgorithm = false;

    public CollisionSpeedTestScreen() {
        title = new TextRenderable("Collision speed test", 0.75f);
        title.centerX();
        title.setY(Gdx.graphics.getHeight() - title_padding);
        ball = new Ball();
        ball.setDeltaY(0);
        ball.setSpeed(1);
        increaseSpeed = new Button("Increase speed", true, (x, y, b) -> {
            ball.setSpeed(ball.getSpeed() + 1);
        }, Game.getInstance(), 0.5f);
        increaseSpeed.centerY();
        decreaseSpeed = new Button("Decrease speed", true, (x, y, b) -> {
            ball.setSpeed(ball.getSpeed() - 1);
        }, Game.getInstance(), 0.5f);
        decreaseSpeed.centerY();
        decreaseSpeed.setX(Gdx.graphics.getWidth() - decreaseSpeed.getWidth());
        switchAlgorithm = new Button("Switch algorithm", true, (x, y, b) -> {
            oldAlgorithm = !oldAlgorithm;
        }, Game.getInstance(), 0.5f);
        switchAlgorithm.centerX();
        leftCollider = new StaticCollider("left");
        rightCollider = new StaticCollider("right");
        algoStatus = new TextRenderable(0.5f);
        algoStatus.setY(switchAlgorithm.getY() + switchAlgorithm.getHeight());
        speedText = new TextRenderable(0.5f);
        speedText.setY(title.getY());
        resetButton = new Button("Reset", true, (x, y, b) -> {
            ball.setSpeed(1);
            ball.center();
        }, Game.getInstance(), 0.5f);
        resetButton.centerX();
        resetButton.setY(switchAlgorithm.getHeight());
    }

    @Override
    public void render() {
        if (oldAlgorithm) {
            handleCollisions();
        } else {
            handleCollisionProjection();
        }
        algoStatus.setText("oldAlgorithm: " + oldAlgorithm);
        algoStatus.render();
        speedText.setText("speed: " + ball.getSpeed());
        speedText.render();
        resetButton.render();
        title.render();
        ball.render();
        increaseSpeed.render();
        decreaseSpeed.render();
        switchAlgorithm.render();
        leftCollider.render();
        rightCollider.render();
    }

    private void handleCollisionProjection() {
        if (CollisionUtility.isCollidingWith(
                new Vector2(leftCollider.getX(), leftCollider.getY()),
                new Size(leftCollider.getWidth(), leftCollider.getHeight()),
                new Vector2(ball.getX() + (ball.getDeltaX()) * ball.getSpeed(), ball.getY() + (ball.getDeltaY()) * ball.getSpeed()),
                new Size(ball.getWidth(), ball.getHeight()))) {
            ball.setDeltaX(-ball.getDeltaX());
            while (ball.getX() < leftCollider.getX() + leftCollider.getWidth()) {
                ball.setX(ball.getX() + 1);
            }
        }
        if (CollisionUtility.isCollidingWith(
                new Vector2(rightCollider.getX(), rightCollider.getY()),
                new Size(rightCollider.getWidth(), rightCollider.getHeight()),
                new Vector2(ball.getX() + (ball.getDeltaX()) * ball.getSpeed(), ball.getY() + (ball.getDeltaY()) * ball.getSpeed()),
                new Size(ball.getWidth(), ball.getHeight()))) {
            ball.setDeltaX(-ball.getDeltaX());
            while (ball.getX() > rightCollider.getX() + rightCollider.getWidth()) {
                ball.setX(ball.getX() - 1);
            }
        }
    }

    public void handleCollisions() {
        if (CollisionUtility.isCollidingWith(leftCollider, ball)) {
            ball.setDeltaX(-ball.getDeltaX());
        }
        if (CollisionUtility.isCollidingWith(rightCollider, ball)) {
            ball.setDeltaX(-ball.getDeltaX());
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
        if (CollisionUtility.isCollidingWithWall(ball, Wall.Left)) {
            while (ball.getX() < 0) {
                ball.setX(ball.getX() + 1);
            }
            ball.setDeltaX(-ball.getDeltaX());
        }
        if (CollisionUtility.isCollidingWithWall(ball, Wall.Right)) {
            while (ball.getX() > Gdx.graphics.getWidth()) {
                ball.setX(ball.getX() - 1);
            }
            ball.setDeltaX(-ball.getDeltaX());
        }
    }
}
