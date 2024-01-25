package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Objects;

/**
 * <p>
 *     displays a dialog box with either an "OK" button or "Yes" and "No" buttons.
 *     a {@code DialogListener} can be added to control the behaviour of the buttons.
 * </p>
 * <b>
 *     WARNING: in order for this dialog box to be rendered above all other members in a {@code Screen}, it must
 *     be the last member added to the {@code Screen}.
 * </b>
 */
public class DialogBox extends Renderable implements Transformable {
    private float x;
    private float y;
    private float width;
    private float height;
    private final TransformableRect background;
    private final TextRenderable prompt;
    private final DialogType type;
    private Button ok;
    private Button yes;
    private Button no;
    private final DialogListener responseListener;
    private final InputManager inputManager;
    private final TransformableRect tint;
    private boolean hidden = true;
    private final float button_padding = 25;

    public DialogBox(DialogType type, String prompt, DialogListener responseListener, InputManager inputManager) {
        background = new TransformableRect(Color.DARK_GRAY);
        tint = new TransformableRect();
        tint.setWidth(Gdx.graphics.getWidth());
        tint.setHeight(Gdx.graphics.getHeight());
        tint.setColor(new Color(0, 0, 0, 0.75f));
        this.inputManager = inputManager;
        setWidth(((float) Gdx.graphics.getWidth() / 7) * 4);
        setHeight(((float) Gdx.graphics.getHeight() / 7) * 3);
        center();
        if (responseListener == null && type == DialogType.Information) {
            this.responseListener = new DialogListenerAdapter() {
                @Override
                public void onOk() {
                    toggle();
                }
            };
        } else {
            this.responseListener = Objects.requireNonNullElseGet(responseListener, () -> new DialogListenerAdapter() {
            });
        }
        this.type = type;
        this.prompt = new TextRenderable(prompt, DefaultTextScaling.SUBTITLE);
        positionText();
        layoutButtons();
    }

    public DialogBox(DialogType type, String prompt, InputManager inputManager) {
        this(type, prompt, null, inputManager);
    }

    private void positionText() {
        prompt.centerRelativeTo(this, true);
        prompt.setY((prompt.getY() + (getHeight() / 3)) - prompt.getHeight() / 2);
        prompt.setMaxWidth(getWidth());
        // multiplying by 2 will set a gap of 50 pixels each
        // side of the prompt.
        prompt.setAutoScalePadding(50 * 2);
    }

    private void layoutButtons() {
        switch (type) {
            case Information -> {
                ok = new Button("OK", true, (x, y, b) -> {
                    responseListener.onOk();
                }, inputManager, DefaultTextScaling.BUTTON);
                ok.centerXRelativeTo(this);
                ok.setY(getY() + button_padding);
            }
            case Question -> {
                yes = new Button("Yes", true, (x, y, b) -> {
                    responseListener.onYes();
                }, inputManager, DefaultTextScaling.BUTTON);
                yes.setY(getY() + button_padding);
                yes.setX(getX() + button_padding);
                no = new Button("No", true, (x, y, b) -> {
                    responseListener.onNo();
                }, inputManager, DefaultTextScaling.BUTTON);
                no.setY(getY() + button_padding);
                no.setX(getX() + getWidth() - button_padding - no.getWidth());
            }
        }
    }

    @Override
    public void render() {
        if (hidden) return;
        tint.render();
        background.setWidth(getWidth());
        background.setHeight(getHeight());
        background.setX(getX());
        background.setY(getY());
        background.render();
        prompt.render();
        // this might cause problems in the future, but for now we're going to clear
        // any input processors before setting ours to give our buttons precedence
        // over any other buttons being rendered behind our dialog box.
        inputManager.clearInputProcessors();
        switch (type) {
            case Information -> ok.render();
            case Question -> {
                yes.render();
                no.render();
            }
        }
    }

    public void setPrompt(String prompt) {
        this.prompt.setText(prompt);
        positionText();
    }

    public String getPrompt() {
        return prompt.getText();
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

    public void toggle() {
        hidden = !hidden;
    }
}
