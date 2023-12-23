package net.approachcircle.game.backend.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.Acceleratable;
import net.approachcircle.game.backend.ShapeRenderable;
import net.approachcircle.game.backend.Transformable;

public class MovingCollider extends ShapeRenderable implements Acceleratable, Transformable {
    private float width;
    private float height;
    private float x;
    private float y;
    private float speed;
    private final float initial_speed = 1;
    private final float speed_increase_step = 1;
    private float deltaX = 1;
    private float deltaY = 1;

    public MovingCollider() {
        setWidth(20);
        setHeight(20);
        center();
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

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void render() {
        setX(getX() + (deltaX * speed));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
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
}
