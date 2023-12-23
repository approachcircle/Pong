package net.approachcircle.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.approachcircle.game.backend.ScreenUtility;

public class Opponent extends Player {
    public Opponent() {
        setX(Gdx.graphics.getWidth() - getWidth() - padding);
        setY(ScreenUtility.getScreenCenter().y - (getHeight() / 2));
    }
    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
    }

    public void setDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case Easy -> setSpeed(getSpeed() - 5);
            case Normal -> setSpeed(getSpeed() - 2);
            case Hard -> setSpeed(getSpeed() + 4);
        }
    }
}
