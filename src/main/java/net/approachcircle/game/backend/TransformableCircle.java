package net.approachcircle.game.backend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TransformableCircle extends ShapeRenderable implements Transformable {
    private float radius;
    private Color color;
    private float x;
    private float y;

    public TransformableCircle(Color color) {
        this.color = color;
        setX(0);
        setY(0);
        setWidth(15);
    }

    public TransformableCircle() {
        this(Color.WHITE);
    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(getX(), getY(), getWidth());
        shapeRenderer.end();
    }

    @Override
    public float getWidth() {
        return radius;
    }

    @Override
    public float getHeight() {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
