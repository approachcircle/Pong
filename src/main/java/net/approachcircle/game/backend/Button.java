package net.approachcircle.game.backend;

public class Button extends BasicRenderable implements ITransformable {
    private float x;
    private float y;
    private final boolean background;
    private ButtonBackground buttonBackground;
    private final TextRenderable textRenderable;
    private final float padding = 30;

    //TODO: implement button click event listener
    public Button(String text, boolean background) {
        this.background = background;
        textRenderable = new TextRenderable(text, 0.75f);
        if (background) {
            buttonBackground = new ButtonBackground();
            setWidth(textRenderable.getWidth() - textRenderable.getScale() + padding);
            setHeight(textRenderable.getHeight() + padding + textRenderable.getScale());
        }
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
        return buttonBackground.getWidth();
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
    }

    @Override
    public void setY(float y) {
        this.y = y;
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
