package net.approachcircle.game.backend;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingAnimation extends Renderable implements Transformable {
    private float width;
    private float height;
    private float x;
    private float y;
    private final SpriteBatch batch;
    private int frame = 0;

    public LoadingAnimation() {
        batch = new SpriteBatch();
        setWidth(100);
        setHeight(100);
        setX(50);
        setY(50);
    }

    @Override
    public void render() {
        if (frame >= 60) {
            frame = 0;
        }
        Texture texture = new Texture(Gdx.files.getFileHandle(String.format("loading/loading_%d.png", frame), Files.FileType.Classpath));
        batch.begin();
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.end();
        frame++;
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
