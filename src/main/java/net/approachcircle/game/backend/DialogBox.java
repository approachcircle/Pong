package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class DialogBox implements Renderable, Transformable {
    private float x;
    private float y;
    private float width;
    private float height;
    private TransformableRect background;
    TextRenderable prompt;
    DialogType type;

    public DialogBox(DialogType type, String prompt) {
        background = new TransformableRect(Color.DARK_GRAY);
        setWidth(((float)Gdx.graphics.getWidth() / 7) * 4);
        setHeight(((float)Gdx.graphics.getHeight() / 7) * 3);
        center();
        this.type = type;
        this.prompt = new TextRenderable(prompt, 0.75f);
        this.prompt.centerRelativeTo(this, true);
        this.prompt.setY(this.prompt.getY() + (getHeight() / 3));
        this.prompt.setMaxWidth(getWidth());
        // multiplying by 2 will set a gap of 50 pixels each
        // side of the prompt.
        this.prompt.setAutoScalePadding(50 * 2);
    }

    @Override
    public void render() {
        background.setWidth(getWidth());
        background.setHeight(getHeight());
        background.setX(getX());
        background.setY(getY());
        background.render();
        prompt.render();
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
