package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.*;


public class Player extends ShapeRenderable implements Transformable, Acceleratable {
    private float width;
    private float height;
    private float x;
    private float y;
    protected float speed;
    protected float deltaX = 1;
    protected float deltaY = 1;
    protected final float padding = 100;

    public Player() {
        setWidth(20);
        setHeight(width * 8);
        setX(padding);
        setY(ScreenUtility.getScreenCenter().y - (getHeight() / 2));
        setSpeed(15);
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isKeyPressed(Input.Keys.W))) && getY() <= Gdx.graphics.getHeight() - getHeight()) {
            setY(getY() + (deltaX) * speed);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || (Gdx.input.isKeyPressed(Input.Keys.S))) && getY() > 0) {
            setY(getY() - (deltaY) * speed);
        }
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
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
    public void centerX() {
        setX(ScreenUtility.getScreenCenter().x - (getWidth() / 2));
    }

    @Override
    public void centerY() {
        setY(ScreenUtility.getScreenCenter().y - (getHeight() / 2));
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
}
