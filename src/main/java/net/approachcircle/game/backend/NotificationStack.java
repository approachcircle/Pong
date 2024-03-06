package net.approachcircle.game.backend;

import java.util.Stack;

public class NotificationStack {
    private final Stack<Notification> stack;

    public NotificationStack() {
        stack = new Stack<>();
    }

    public Notification pop() {
        if (stack.isEmpty()) {
            return null;
        }
        Notification previous = stack.pop();
        String log = String.format("notification popped: %s -> %s", previous, peek());
        Logger.info(log);
        return previous;
    }

    public Notification peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public void push(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("pushing a null dialog box?");
        }
        String log = String.format("notification pushed: %s -> %s", peek(), notification);
        stack.push(notification);
        Logger.info(log);
    }

    public void insert(int index, Notification notification) {
        stack.add(index, notification);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }
}
