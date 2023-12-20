package net.approachcircle.game.backend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class ShapeRenderable implements Renderable {
    // DISPOSABLE
    protected ShapeRenderer shapeRenderer = new ShapeRenderer();
}
