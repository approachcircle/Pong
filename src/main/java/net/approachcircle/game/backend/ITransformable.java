package net.approachcircle.game.backend;

public interface ITransformable extends IScalable, ITranslatable {
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
    default void centerXRelativeTo(ITransformable transformable) {
        // setX(transformable.getX() + (transformable.getWidth() - getWidth()) / 2);
        setX((transformable.getX() + (transformable.getWidth() / 2)) - getWidth() / 2);
    }
    default void centerYRelativeTo(ITransformable transformable) {
        setY(transformable.getY() + (transformable.getHeight() + getHeight()) / 2);
    }
    default void centerRelativeTo(ITransformable transformable) {
        centerXRelativeTo(transformable);
        centerYRelativeTo(transformable);
    }
}
