package net.approachcircle.game.backend.tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.ShapeRenderable;
import net.approachcircle.game.backend.Transformable;

public class StaticCollider extends ShapeRenderable implements Transformable {
    private float x;
    private float y;
    private float width;
    private float height;
    private final float distance = 400;
    public StaticCollider(String position) {
        if (!position.equalsIgnoreCase("left") && !position.equalsIgnoreCase("right")) {
            throw new IllegalArgumentException("static collider position is invalid");
        }
        setWidth(20);
        setHeight(width * 8);
        center();
        if (position.equalsIgnoreCase("left")) {
            setX(getX() - distance);
        } else {
            setX(getX() + getWidth() + distance);
        }
    }
    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
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
