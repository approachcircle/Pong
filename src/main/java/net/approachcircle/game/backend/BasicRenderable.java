package net.approachcircle.game.backend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class BasicRenderable {
    protected ShapeRenderer shapeRenderer = new ShapeRenderer();
    public abstract void render();
}
