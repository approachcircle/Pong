package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;

public class TextEntry implements Renderable, Transformable {
    private float x;
    private float y;
    private final TransformableRect background;
    private final TextRenderable text;
    // calculate position of caret with text.getX() + text.getWidth()
    private final TransformableRect caret;
    private final InputManager inputManager;
    private InputAdapter inputProcessor;
    private boolean shiftHeld = false;
    private boolean ctrlHeld = false;
    private boolean altGrHeld = false;
    private int elapsed = 0;
    private final int caretFlashRate = 30; // flash caret every half second
    private boolean caretVisible = true;
    private final float TEXT_PADDING = 20;
    private boolean hasFocus = false;

    public TextEntry(float width, float height, InputManager inputManager) {
        this.inputManager = inputManager;
        background = new TransformableRect(Color.GRAY);
        setWidth(width + TEXT_PADDING);
        setHeight(height + TEXT_PADDING);
        text = new TextRenderable(0.5f);
        caret = new TransformableRect(Color.WHITE);
        caret.setWidth(2);
    }
    @Override
    public void render() {
        if (!hasFocus) {
            elapsed = 60;
            caretVisible = false;
        } else {
            elapsed++;
            if (elapsed % caretFlashRate == 0) {
                caretVisible = !caretVisible;
            }
        }
        background.setX(getX());
        background.setY(getY());
        caret.setHeight(background.getHeight());
        text.centerRelativeTo(background, true);
        background.render();
        text.render();
        caret.setX(text.getX() + text.getWidth());
        caret.setY(background.getY());
        if (caretVisible) {
            caret.render();
        }
        updateInputProcessor();
    }

    private void updateInputProcessor() {
        // no input manager or listener provided, don't bother setting input processor
        if (inputManager == null) {
            return;
        }
        if (inputProcessor != null) {
            inputManager.removeInputProcessor(inputProcessor);
        }
        inputProcessor = new InputAdapter() {
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                hasFocus = x >= getX() && x <= getX() + getWidth() && Gdx.graphics.getHeight() - y >= getY() && Gdx.graphics.getHeight() - y <= getY() + getHeight();
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (!hasFocus) {
                    return false;
                }
                caretVisible = true;
                elapsed = 0;
                if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
                    shiftHeld = true;
                    return true;
                }
                if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
                    ctrlHeld = true;
                    return true;
                }
                if (keycode == Input.Keys.ALT_RIGHT) {
                    altGrHeld = true;
                    return true;
                }
                if (keycode == Input.Keys.ENTER || keycode == Input.Keys.ESCAPE) {
                    hasFocus = false;
                    return true;
                }
                if (keycode == Input.Keys.DEL) {
                    if (text.getText().isEmpty()) {
                        return false;
                    }
                    if (ctrlHeld) {
                        text.setText("");
                        return true;
                    }
                    text.setText(text.getText().substring(0, text.getText().length() - 1));
                    return true;
                }
                if (atCapacity()) {
                    return false;
                }
                if (keycode == Input.Keys.SPACE) {
                    textAppend(" ");
                    return true;
                }
                String converted = LayoutConverter.convert(keycode, shiftHeld, altGrHeld);
                if (!converted.isEmpty()){
                    textAppend(converted);
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (!hasFocus) {
                    return false;
                }
                if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
                    shiftHeld = false;
                    return true;
                }
                if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
                    ctrlHeld = false;
                    return true;
                }
                if (keycode == Input.Keys.ALT_RIGHT) {
                    altGrHeld = false;
                    return true;
                }
                return false;
            }
        };
        inputManager.addInputProcessor(inputProcessor);
    }

    private boolean atCapacity() {
        return text.getWidth() >= background.getWidth() - TEXT_PADDING;
    }

    private void textAppend(String data) {
        text.setText(text.getText() + data);
    }

    @Override
    public float getWidth() {
        return background.getWidth();
    }

    @Override
    public float getHeight() {
        return background.getHeight();
    }

    @Override
    public void setWidth(float width) {
        background.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        background.setHeight(height);
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
}
