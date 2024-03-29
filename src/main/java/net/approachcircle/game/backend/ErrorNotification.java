package net.approachcircle.game.backend;

import com.badlogic.gdx.graphics.Color;

public class ErrorNotification extends Notification implements Transformable {
    private final TextRenderable prompt;
    private final TransformableRect background;
    private int timeAlive = 0;
    private final int timeToLiveSeconds = 4;
    private final int animationSpeed = 12;
    private final NotificationListener eventListener;

    public ErrorNotification(String prompt, NotificationListener eventListener) {
        this.prompt = new TextRenderable(prompt, DefaultTextScaling.SMALL);
        this.prompt.setColor(Color.RED);
        this.background = new TransformableRect(new Color(255, 0, 0, 0.2f));
        this.background.setWidth(this.prompt.getWidth() + 25);
        this.background.setHeight(this.prompt.getHeight() + 25);
        this.background.centerX();
        setY(-this.background.getHeight());
        this.eventListener = eventListener;
    }

    public ErrorNotification(String prompt) {
        this(prompt, new NotificationListenerAdapter() {});
    }

    @Override
    public String getPrompt() {
        return prompt.getText();
    }

    @Override
    public void setPrompt(String prompt) {
        this.prompt.setText(prompt);
    }

    @Override
    public void render() {
        if (timeAlive == 0) {
            eventListener.onEnter();
        }
        timeAlive++;
        prompt.centerRelativeTo(background);
        if (timeAlive < timeToLiveSeconds * 60) {
            if (getY() < 0) {
                setY(getY() + animationSpeed);
            }
        } else {
            if (getY() > -background.getHeight()) {
                setY(getY() - animationSpeed);
            } else {
                eventListener.onExit();
                kill();
            }
        }
        background.render();
        prompt.render();
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
        return background.getX();
    }

    @Override
    public float getY() {
        return background.getY();
    }

    @Override
    public void setX(float x) {
        background.setX(x);
    }

    @Override
    public void setY(float y) {
        background.setY(y);
    }
}
