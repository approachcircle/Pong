package net.approachcircle.game.backend;

import java.util.Stack;

public class ScreenStack {
    private final Stack<Screen> stack;
    private final InputManager inputManager;
    public ScreenStack(InputManager inputManager) {
        this.inputManager = inputManager;
        stack = new Stack<>();
    }

    public Screen pop() {
        if (stack.isEmpty()) {
            return null;
        }
        inputManager.clearInputProcessors();
        Screen previous = stack.pop();
        String log = String.format("screen popped: %s -> %s", previous, peek());
        System.out.println(log);
        return previous;
    }

    public void push(Screen screen) {
        String log = String.format("screen pushed: %s -> %s", peek(), screen);
        inputManager.clearInputProcessors();
        stack.push(screen);
        System.out.println(log);
    }

    public Screen peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public void insert(int index, Screen screen) {
        stack.add(index, screen);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }
}
