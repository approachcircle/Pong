package net.approachcircle.game.backend;

public interface Acceleratable extends Translatable {
    float getDeltaX();
    void setDeltaX(float deltaX);
    float getDeltaY();
    void setDeltaY(float deltaY);
    float getSpeed();
    void setSpeed(float speed);
}
