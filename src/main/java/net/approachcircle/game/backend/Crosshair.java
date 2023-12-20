package net.approachcircle.game.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Crosshair extends BasicRenderable {
    private ArrayList<Integer> toggleKeys = new ArrayList<>(List.of(new Integer[] {
            Input.Keys.CONTROL_LEFT,
            Input.Keys.ALT_LEFT,
            Input.Keys.C
    }));
    public boolean enabled = false;
    @Override
    public void render() {
        if (KeyCombo.isPressed(toggleKeys)) {
            enabled = !enabled;
        }
        if (!enabled) {
            return;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, ScreenUtility.getScreenCenter().y - 1, Gdx.graphics.getWidth(), 2);
        shapeRenderer.rect(ScreenUtility.getScreenCenter().x - 1, 0, 2, Gdx.graphics.getHeight());
        shapeRenderer.end();
    }
}
