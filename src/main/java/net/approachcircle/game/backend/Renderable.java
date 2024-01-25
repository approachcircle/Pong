package net.approachcircle.game.backend;

public abstract class Renderable {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public abstract void render();
}
