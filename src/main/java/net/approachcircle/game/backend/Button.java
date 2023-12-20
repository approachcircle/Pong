package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import net.approachcircle.game.Game;

public class Button extends BasicRenderable implements Transformable {
    private float x;
    private float y;
    private final boolean background;
    private ButtonBackground buttonBackground;
    private final TextRenderable textRenderable;
    private final float padding = 30;
    private final ButtonClickListener listener;
    private InputAdapter inputProcessor;

    public Button(String text, boolean background, float x, float y, ButtonClickListener listener) {
        this.background = background;
        this.listener = listener;
        setX(x);
        setY(y);
        textRenderable = new TextRenderable(text, 0.75f, getX(), getY());
        if (background) {
            buttonBackground = new ButtonBackground();
            setWidth(textRenderable.getWidth() - textRenderable.getScale() + padding);
            setHeight(textRenderable.getHeight() + padding + textRenderable.getScale());
        }
    }

    public Button(String text, boolean background) {
        this(text, background, 0, 0, (x, y, b) -> {});
    }

    public Button(String text, boolean background, ButtonClickListener listener) {
        this(text, background, 0, 0, listener);
    }

    private void updateInputProcessor() {
        if (inputProcessor != null) {
            Game.getInstance().removeInputProcessor(inputProcessor);
        }
        inputProcessor = new InputAdapter() {
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                // cursor coordinates must be subtracted from screen height to get absolute coordinates
                if (x >= getX() && x <= getX() + getWidth() && Gdx.graphics.getHeight() - y >= getY() && Gdx.graphics.getHeight() -  y <= getY() + getHeight()) {
                    if (button == Input.Buttons.LEFT) {
                        listener.onClick(x, Gdx.graphics.getHeight() - y, button);
                        return true;
                    }
                }
                return false;
            }
        };
        Game.getInstance().addInputProcessor(inputProcessor);
        // Gdx.input.setInputProcessor();
    }

    @Override
    public void render() {
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
