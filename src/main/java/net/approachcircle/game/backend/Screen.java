package net.approachcircle.game.backend;

public abstract class Screen implements Renderable {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Other;
    }
}
