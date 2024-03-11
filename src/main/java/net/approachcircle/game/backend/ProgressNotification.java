package net.approachcircle.game.backend;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class ProgressNotification extends Notification implements Transformable {
    private final TextRenderable prompt;
    private final TransformableRect background;
    private final LoadingAnimation loading;
    private final TransformableTexture completedTexture;
    private boolean isCompleted = false;
    private int timeAlive = 0;
    private final int animationSpeed = 12;
    private final NotificationListener eventListener;

    public ProgressNotification(String prompt, NotificationListener eventListener) {
        completedTexture = new TransformableTexture(Gdx.files.getFileHandle("tick.png", Files.FileType.Classpath));
        loading = new LoadingAnimation();
        this.prompt = new TextRenderable(prompt, DefaultTextScaling.SMALL);
        this.prompt.setColor(Color.WHITE);
        Color bgColor = new Color(Color.GRAY);
        bgColor.a = 0.2f;
        this.background = new TransformableRect(bgColor);
        this.background.setWidth(this.prompt.getWidth() + 25);
        this.background.setHeight(this.prompt.getHeight() + 25);
        this.background.centerX();
        setY(-this.background.getHeight());
        this.eventListener = eventListener;
    }

    public ProgressNotification(String prompt) {
        this(prompt, new NotificationListenerAdapter() {});
    }


    @Override
    public String getPrompt() {
        return prompt.getText();
    }

    @Override
    public void setPrompt(String prompt) {
        if (!isCompleted) {
            this.prompt.setText(prompt);
        }
    }

    public void render() {
        if (timeAlive == 0) {
            eventListener.onEnter();
        }
        timeAlive++;
        prompt.centerRelativeTo(background);
        if (!isCompleted) {
            if (getY() < background.getHeight()) {
                setY(getY() + animationSpeed);
            }
        } else {
            if (getY() > -background.getHeight()) {
                setY(getY() - (animationSpeed - 8));
            } else {
                eventListener.onExit();
                kill();
            }
        }
        if (!isCompleted) {
            loading.setHeight(background.getHeight());
            loading.setWidth(background.getHeight());
            loading.setX(background.getX() - (loading.getWidth() / 2) - 50);
            loading.centerYRelativeTo(background);
            loading.setY(loading.getY() - loading.getHeight());
            loading.render();
        } else {
            completedTexture.setHeight(background.getHeight());
            completedTexture.setWidth(background.getHeight());
            completedTexture.setX(background.getX() - (completedTexture.getWidth() / 2) - 50);
            completedTexture.centerYRelativeTo(background);
            completedTexture.setY(completedTexture.getY() - completedTexture.getHeight());
            completedTexture.render();
        }
        this.background.setWidth(this.prompt.getWidth() + 25);
        this.background.setHeight(this.prompt.getHeight() + 25);
        this.background.centerX();
        background.render();
        prompt.render();
    }

    public void setCompleted() {
        setPrompt("done!");
        isCompleted = true;
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
