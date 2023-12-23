package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import net.approachcircle.game.Wall;

public class CollisionUtility {
    public static boolean isCollidingWith(Transformable first, Transformable second) {
        return (first.getX() <= second.getX() + second.getWidth() &&
                first.getX() + first.getWidth() >= second.getX() - (second.getWidth() * 1.25f) &&
                first.getY() + (second.getHeight() / 2) <= second.getY() + second.getHeight() &&
                first.getY() + first.getHeight() >= second.getY() - (first.getHeight() / 2));
    }
    public static boolean isCollidingWith(Vector2 firstPosition, Size firstSize, Vector2 secondPosition, Size secondSize) {
        return (firstPosition.x <= secondPosition.x + secondSize.width &&
                firstPosition.x + firstSize.width >= secondPosition.x - (secondSize.width * 1.25f) &&
                firstPosition.y + (secondSize.height / 2) <= secondPosition.y + secondSize.height &&
                firstPosition.y + firstSize.height >= secondPosition.y - (firstSize.height / 2));
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
