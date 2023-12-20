package net.approachcircle.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.*;

public class Ball extends BasicRenderable implements Acceleratable, Transformable {
    private float x;
    private float y;
    private float radius;
    private float speed;
    private final float initialSpeed = 6;
    private final float speedIncline = .75f;
    private float deltaX = 1;
    private float deltaY = 1;
    private static Ball instance;

    private Ball() {
        setSpeed(initialSpeed);
        setWidth(25);
        setX(ScreenUtility.getScreenCenter().x);
        setY(ScreenUtility.getScreenCenter().y);
    }

    public static Ball getInstance() {
        if (instance == null) {
            instance = new Ball();
        }
        return instance;
    }

    public void render() {
        handleCollisions();
        setX(getX() + (deltaX) * speed);
        setY(getY() + (deltaY) * speed);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(getX(), getY(), getWidth());
        shapeRenderer.end();
    }

    public void handleCollisions() {
        if (CollisionUtility.isCollidingWith(Player.getInstance(), this)) {
            setDeltaX(-getDeltaX());
            setSpeed(getSpeed() + speedIncline);
            return;
        }
        if (CollisionUtility.isCollidingWith(Opponent.getInstance(), this)) {
            setDeltaX(-getDeltaX());
            setSpeed(getSpeed() + speedIncline);
            return;
        }
        if (CollisionUtility.isCollidingWithWall(this, Wall.Left)) {
            Score.getInstance().incrementOpponent();
            center();
            resetSpeed();
            return;
        }
        if (CollisionUtility.isCollidingWithWall(this, Wall.Right)) {
            Score.getInstance().incrementPlayer();
            center();
            resetSpeed();
            return;
        }
        if (CollisionUtility.isCollidingWithWall(this, Wall.Top) ||
                CollisionUtility.isCollidingWithWall(this, Wall.Bottom)) {
            setDeltaY(-getDeltaY());
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getHeight() {
        return radius;
    }

    @Override
    public float getWidth() {
        return radius;
    }

    @Override
    public void setWidth(float width) {
        radius = width;
    }

    @Override
    public void setHeight(float height) {
        radius = height;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void resetSpeed() {
        speed = initialSpeed;
    }

    @Override
    public float getDeltaX() {
        return deltaX;
    }

    @Override
    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }

    @Override
    public float getDeltaY() {
        return deltaY;
    }

    @Override
    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }
}

