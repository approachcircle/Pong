package net.approachcircle.game.backend;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextRenderable implements Transformable, Renderable {
    // DISPOSABLE
    private final BitmapFont font;
    private final Batch batch = new SpriteBatch();
    private GlyphLayout glyphLayout;
    private float x;
    private float y;
    private float maxWidth;
    private boolean recalculateCenterX = false;
    private boolean recalculateCenterY = false;
    private Transformable recalculateCenterXRelative;
    private Transformable recalculateCenterYRelative;
    private float scale;
    private Color color;
    private String text;
    private int autoScalePadding = 0;

    public TextRenderable(String text, float scale, Color color) {
        FileHandle handle = Gdx.files.getFileHandle("yu_gothic_ui.fnt", Files.FileType.Classpath);
        font = new BitmapFont(handle);
        this.x = 0;
        this.y = 0;
        this.maxWidth = 0;
        this.scale = scale;
        font.getData().setScale(scale);
        this.text = text;
        setText(text);
        setColor(color);
    }

    public TextRenderable(String text, float scale) {
        this(text, scale, Color.WHITE);
    }

    public TextRenderable(String text, Color color) {
        this(text, 1, color);
    }
    public TextRenderable(String text) {
        this(text, 1, Color.WHITE);
    }

    public TextRenderable(float scale, Color color) {
        this("", scale, color);
    }

    public TextRenderable(float scale) {
        this("", scale, Color.WHITE);
    }

    public TextRenderable(Color color) {
        this("", 1, color);
    }

    public TextRenderable() {
        this("", 1, Color.WHITE);
    }

    @Override
    public void render() {
        if (recalculateCenterX) {
            centerX(true);
        }
        if (recalculateCenterY) {
            centerY(true);
        }
        if (maxWidth > 0) {
            scaleToMaxWidth();
        }
        if (recalculateCenterXRelative != null) {
            centerXRelativeTo(recalculateCenterXRelative, true);
        }
        if (recalculateCenterYRelative != null) {
            centerYRelativeTo(recalculateCenterYRelative, true);
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
        recalculateCenterXRelative = null;
        this.x = x;
    }

    @Override
    public void setY(float y) {
        recalculateCenterY = false;
        recalculateCenterYRelative = null;
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

    public void centerXRelativeTo(Transformable transformable, boolean recalculate) {
        if (recalculate) {
            recalculateCenterXRelative = transformable;
        }
        x = (transformable.getX() + (transformable.getWidth() / 2)) - getWidth() / 2;
    }

    public void centerXRelativeTo(Transformable transformable) {
        centerXRelativeTo(transformable, false);
    }

    public void centerYRelativeTo(Transformable transformable, boolean recalculate) {
        if (recalculate) {
            recalculateCenterYRelative = transformable;
        }
        y = transformable.getY() + (transformable.getHeight() + getHeight()) / 2;
    }

    @Override
    public void centerYRelativeTo(Transformable transformable) {
        centerYRelativeTo(transformable, false);
    }

    @Override
    public void centerRelativeTo(Transformable transformable) {
        centerXRelativeTo(transformable);
        centerYRelativeTo(transformable);
    }

    public void centerRelativeTo(Transformable transformable, boolean recalculate) {
        centerXRelativeTo(transformable, recalculate);
        centerYRelativeTo(transformable, recalculate);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateGlyphLayout();
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    private void scaleToMaxWidth() {
        // multiply padding by either side to calculate gap
        // either side
        while (getWidth() + autoScalePadding > maxWidth) {
            setScale(getScale() - 0.001f);
        }
    }

    public void setAutoScalePadding(int padding) {
        autoScalePadding = padding;
        scaleToMaxWidth();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        font.getData().setScale(scale);
        updateGlyphLayout();
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

    private void updateGlyphLayout() {
        glyphLayout = new GlyphLayout(font, text);
    }

    public void setColor(Color color) {
        this.color = color;
        font.setColor(color);
        updateGlyphLayout();
    }

    public Color getColor() {
        return color;
    }
}
