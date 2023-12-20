package net.approachcircle.game.backend;

public interface Transformable extends Scalable, Translatable {
    default void centerX() {
        setX(ScreenUtility.getScreenCenter().x - (getWidth() / 2));
    }
    default void centerY() {
        setY(ScreenUtility.getScreenCenter().y - (getHeight() / 2));
    }
    default void center() {
        centerX();
        centerY();
    }
    default void centerXRelativeTo(Transformable transformable) {
        // setX(transformable.getX() + (transformable.getWidth() - getWidth()) / 2);
        setX((transformable.getX() + (transformable.getWidth() / 2)) - getWidth() / 2);
    }
    default void centerYRelativeTo(Transformable transformable) {
        setY(transformable.getY() + (transformable.getHeight() + getHeight()) / 2);
    }
    default void centerRelativeTo(Transformable transformable) {
        centerXRelativeTo(transformable);
        centerYRelativeTo(transformable);
    }
}
