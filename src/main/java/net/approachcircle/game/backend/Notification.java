package net.approachcircle.game.backend;

public abstract class Notification extends Renderable {
    private boolean alive = true;
    public abstract String getPrompt();

    public abstract void setPrompt(String prompt);

    /**
     * this method should be used to mark a notification as being due to be popped off its stack
     * without having to inject the {@code NotificationGroup} into the notification itself.
     */
    protected void kill() {
        alive = false;
    }
    public boolean isAlive() {
        return alive;
    }

    @Override
    public String toString() {
        if (this instanceof DialogBox current) {
            return String.format("(%s): %s", current.getType().name().toLowerCase(), getTrimmedPrompt());
        }
        return String.format("(%s): %s", getClass().getSimpleName(), getTrimmedPrompt());
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
