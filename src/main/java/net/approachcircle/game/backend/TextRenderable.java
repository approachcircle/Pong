package net.approachcircle.game.backend;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextRenderable extends BasicRenderable implements Transformable {
    private final BitmapFont font;
    private final Batch batch = new SpriteBatch();
    private GlyphLayout glyphLayout;
    private float x;
    private float y;
    private boolean recalculateCenterX = false;
    private boolean recalculateCenterY = false;
    private final float scale;

    public TextRenderable(String text, float scale, float x, float y) {
        font = new BitmapFont(new FileHandle("src/main/resources/yu_gothic_ui.fnt"));
        this.x = x;
        this.y = y;
        this.scale = scale;
        font.getData().setScale(scale);
        setText(text);
    }

    public TextRenderable(String text, float scale) {
        this(text, scale, 0, 0);
    }

    public TextRenderable(String text, float x, float y) {
        this(text, 1, x, y);
    }

    public TextRenderable(String text) {
        this(text, 1, 0, 0);
    }


    public TextRenderable(float scale, float x, float y) {
        this("", scale, x, y);
    }

    public TextRenderable(float scale) {
        this("", scale, 0, 0);
    }

    public TextRenderable(float x, float y) {
        this("", 1, x, y);
    }
    public TextRenderable() {
        this("", 1, 0, 0);
    }

    @Override
    public void render() {
        if (recalculateCenterX) {
            centerX(true);
        }
        if (recalculateCenterY) {
            centerY(true);
        }
        batch.begin();
        font.draw(batch, glyphLayout, getX(), getY());
        batch.end();
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
        recalculateCenterX = false;
        this.x = x;
    }

    @Override
    public void setY(float y) {
        recalculateCenterY = false;
        this.y = y;
    }

    @Override
    public void center() {
        center(false);
    }

    public void center(boolean recalculate) {
        centerX(recalculate);
        centerY(recalculate);
    }

    @Override
    public void centerX() {
        centerX(false);
    }

    @Override
    public void centerY() {
        centerY(false);
    }

    public void centerX(boolean recalculate) {
        recalculateCenterX = recalculate;
        // directly access x to bypass the center recalculate disable behaviour
        x = ScreenUtility.getScreenCenter().x - (glyphLayout.width / 2) - scale;
    }

    public void centerY(boolean recalculate) {
        recalculateCenterY = recalculate;
        // directly access y to bypass the center recalculate disable behaviour
        y = ScreenUtility.getScreenCenter().y + (glyphLayout.height / 2) + scale;
    }

    public String getText() {
        return glyphLayout.toString();
    }

    public void setText(String text) {
        glyphLayout = new GlyphLayout(font, text);
    }

    public float getScale() {
        return scale;
    }

    @Override
    public float getWidth() {
        return glyphLayout.width;
    }

    @Override
    public float getHeight() {
        return glyphLayout.height;
    }

    @Override
    public void setWidth(float width) {
        throw new RuntimeException("though this is transformable, the width may not be set manually, only scaled");
    }

    @Override
    public void setHeight(float height) {
        throw new RuntimeException("though this is transformable, the height may not be set manually, only scaled");
    }
}
