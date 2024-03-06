package net.approachcircle.game.backend;

import java.util.Stack;

public class NotificationStack {
    private final Stack<Notification> stack;
    private final InputManager inputManager;

    public NotificationStack(InputManager inputManager) {
        stack = new Stack<>();
        this.inputManager = inputManager;
    }

    public Notification pop() {
        if (stack.isEmpty()) {
            return null;
        }
        //inputManager.clearInputProcessors();
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
        //inputManager.clearInputProcessors();
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
