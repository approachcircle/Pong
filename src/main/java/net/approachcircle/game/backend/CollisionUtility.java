package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import net.approachcircle.game.Wall;

public class CollisionUtility {
    public static boolean isCollidingWith(Transformable first, Transformable second) {
        return (first.getX() + (second.getWidth() / 2) <= second.getX() + second.getWidth() &&
                first.getX() + first.getWidth() >= second.getX() - (second.getWidth() / 2) &&
                first.getY() + (second.getHeight() / 2) <= second.getY() + second.getHeight() &&
                first.getY() + first.getHeight() >= second.getY() - (first.getHeight() / 2));
    }

    public static boolean isCollidingWithWall(Transformable object, Wall wall) {
        switch (wall) {
            case Top -> {
                return object.getY() + object.getHeight() / 2 >= Gdx.graphics.getHeight();
            }
            case Bottom -> {
                return object.getY() - object.getHeight() / 2 < 0;
            }
            case Left -> {
                return object.getX() + object.getWidth() / 2 < 0;
            }
            case Right -> {
                return object.getX() - object.getWidth() / 2 >= Gdx.graphics.getWidth();
            }
            default -> throw new EnumConstantNotPresentException(Wall.class, wall.toString());
        }
    }
}
