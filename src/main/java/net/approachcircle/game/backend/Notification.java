package net.approachcircle.game.backend;

// at some point we may want to give every Notification a onExit and onEnter event handler,
// moving its NotificationListener into here. this will make it not exclusive to
// ErrorNotifications.
public abstract class Notification extends Renderable {
    private boolean alive = true;
    public abstract String getPrompt();

    public abstract void setPrompt(String prompt);

    /**
     * this method should be used to mark a notification as being due to be removed from its
     * {@code NotificationGroup} without having to inject the {@code NotificationGroup} into the notification itself.
     */
    protected void kill() {
        alive = false;
    }
    public boolean isAlive() {
        return alive;
    }

    @Override
    public String toString() {
        String type = getClass().getSimpleName();
        if (this instanceof DialogBox current) {
            type += String.format(" | %s", current.getType().name().toLowerCase());
        }
        return String.format("(%s): %s", type, getTrimmedPrompt());
    }

    private String getTrimmedPrompt() {
        final int previewLength = 30;
        String prompt = "";
        if (getPrompt().length() > previewLength) {
            prompt += getPrompt().substring(0, previewLength);
            prompt += "...";
        } else {
            prompt += getPrompt();
        }
        return prompt;
    }
}
