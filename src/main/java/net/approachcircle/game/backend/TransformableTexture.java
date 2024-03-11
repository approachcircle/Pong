package net.approachcircle.game.backend;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransformableTexture extends Renderable implements Transformable {
    private final Texture texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private final SpriteBatch batch;

    public TransformableTexture(Texture texture) {
        this.batch = new SpriteBatch();
        this.texture = texture;
    }

    public TransformableTexture(FileHandle handle) {
        this(new Texture(handle));
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.end();
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
