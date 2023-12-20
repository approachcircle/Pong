package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.*;


public class Player extends BasicRenderable implements ITransformable, IAcceleratable {
    private float width;
    private float height;
    private float x;
    private float y;
    protected float speed;
    protected float deltaX = 1;
    protected float deltaY = 1;
    private static Player instance;

    protected Player() {
        setWidth(10);
        setHeight((float) Gdx.graphics.getHeight() / 8);
        setX(0);
        setY(ScreenUtility.getScreenCenter().y - (getHeight() / 2));
        setSpeed(15);
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && getY() <= Gdx.graphics.getHeight() - getHeight()) {
            setY(getY() + (deltaX) * speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && getY() > 0) {
            setY(getY() - (deltaY) * speed);
        }
//        if (getY() > Ball.getInstance().getY() - (getHeight() / 2)) {
//            setY(getY() - speed);
//        }
//        if (getY() < Ball.getInstance().getY() - (getHeight() / 2)) {
//            setY(getY() + speed);
//        }
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
