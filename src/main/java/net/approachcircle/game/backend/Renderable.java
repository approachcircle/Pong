package net.approachcircle.game.backend;

public abstract class Renderable {
    private boolean visible = true;
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    public boolean isVisible() {
        return visible;
    }

    /**
     * <b>
     *     WARNING: this method by default does not affect {@code Screen}s unless you implement it yourself by
     *     not calling {@code render()} unless {@code isVisible()} is true.
     * </b>
     */
    public void show() {
        visible = true;
    }

    /**
     * <b>
     *     WARNING: this method by default does not affect {@code Screen}s unless you implement it yourself by
     *     not calling {@code render()} unless {@code isVisible()} is true.
     * </b>
     */
    public void hide() {
        visible = false;
    }

    /**
     * <b>
     *     WARNING: this method by default does not affect {@code Screen}s unless you implement it yourself by
     *     not calling {@code render()} unless {@code isVisible()} is true.
     * </b>
     */
    public void toggleVisibility() {
        visible = !visible;
    }

    public abstract void render();
}
