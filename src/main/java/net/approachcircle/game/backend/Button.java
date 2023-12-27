package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;

public class Button implements Renderable, Transformable {
    private float x;
    private float y;
    private final boolean background;
    private TransformableRect buttonBackground;
    private final TextRenderable textRenderable;
    private final float padding = 50;
    private final ButtonClickListener listener;
    private InputAdapter inputProcessor;
    private final InputManager inputManager;

    public Button(String text, boolean background, ButtonClickListener listener, InputManager inputManager, float scale) {
        this.background = background;
        this.listener = listener;
        this.inputManager = inputManager;
        setX(0);
        setY(0);
        textRenderable = new TextRenderable(text, scale);
        textRenderable.setX(getX());
        textRenderable.setY(getY());
        textRenderable.setColor(Color.WHITE);
        if (background) {
            buttonBackground = new TransformableRect(Color.GRAY);
            setWidth(textRenderable.getWidth() - textRenderable.getScale() + padding);
            setHeight(textRenderable.getHeight() + padding + textRenderable.getScale());
        }
    }
    public Button(String text, boolean background) {
        this(text, background, null, null);
    }
    public Button(String text, boolean background, float scale) {
        this(text, background, null, null, scale);
    }

    public Button(String text, boolean background, ButtonClickListener listener, InputManager inputManager) {
        this(text, background, listener, inputManager, 0.75f);
    }

    private void updateInputProcessor() {
        // no input manager or listener provided, don't bother setting input processor
        if (inputManager == null || listener == null) {
            return;
        }
        if (inputProcessor != null) {
            inputManager.removeInputProcessor(inputProcessor);
        }
        inputProcessor = new InputAdapter() {
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                // cursor coordinates must be subtracted from screen height to get absolute coordinates
                if (x >= getX() && x <= getX() + getWidth() && Gdx.graphics.getHeight() - y >= getY() && Gdx.graphics.getHeight() - y <= getY() + getHeight()) {
                    listener.onClick(x, Gdx.graphics.getHeight() - y, button);
                    return true;
                }
                return false;
            }
        };
        inputManager.addInputProcessor(inputProcessor);
    }

    @Override
    public void render() {
        updateInputProcessor();
        if (background) {
            buttonBackground.setX(getX());
            buttonBackground.setY(getY());
            textRenderable.centerRelativeTo(buttonBackground);
            buttonBackground.render();
        } else {
            textRenderable.setX(getX());
            textRenderable.setY(getY());
        }
        textRenderable.render();
    }

    @Override
    public float getWidth() {
        if (!background) {
            return textRenderable.getWidth();
        }
        return buttonBackground.getWidth();
    }

    @Override
    public float getHeight() {
        if (!background) {
            return textRenderable.getHeight();
        }
        return buttonBackground.getHeight();
    }

    @Override
    public void setWidth(float width) {
        if (!background) {
            throw new IllegalStateException("button has no background, so a width cannot be set. scale the text instead");
        }
        this.buttonBackground.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        if (!background) {
            throw new IllegalStateException("button has no background, so a height cannot be set. scale the text instead");
        }
        this.buttonBackground.setHeight(height);
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
        updateInputProcessor();
    }

    @Override
    public void setY(float y) {
        this.y = y;
        updateInputProcessor();
    }

    @Override
    public void center() {
        centerX();
        centerY();
    }

    @Override
    public void centerX() {
        setX(ScreenUtility.getScreenCenter().x - (buttonBackground.getWidth() / 2));
    }

    @Override
    public void centerY() {
        setY(ScreenUtility.getScreenCenter().y - (buttonBackground.getHeight() / 2));
    }
}
