package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.ScreenUtility;

public class Opponent extends Player {
    private static Opponent instance;
    private Opponent() {
        setX(Gdx.graphics.getWidth() - getWidth());
        setY(ScreenUtility.getScreenCenter().y - (getHeight() / 2));
    }

    public static Opponent getInstance() {
        if (instance == null) {
            instance = new Opponent();
        }
        return instance;
    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        if (getY() > Ball.getInstance().getY() - (getHeight() / 2) && getY() > 0) {
            setY(getY() - speed);
        }
        if (getY() < Ball.getInstance().getY() - (getHeight() / 2) && getY() <= Gdx.graphics.getHeight() - getHeight()) {
            setY(getY() + speed);
        }
        shapeRenderer.end();
    }

    public void setDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case Easy -> setSpeed(getSpeed() - 7);
            case Normal -> setSpeed(getSpeed() - 4);
            case Hard -> setSpeed(getSpeed());
        }
    }
}
