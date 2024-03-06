package net.approachcircle.game.backend;

public abstract class Notification extends Renderable {
    private boolean alive = true;
    public abstract String getPrompt();

    public abstract void setPrompt(String prompt);

    /**
     * this method should be used to mark a notification as being due to be popped off its stack
     * without having to inject the {@code NotificationStack} into the notification itself.
     */
    protected void kill() {
        alive = false;
    }
    public boolean isAlive() {
        return alive;
    }
}
