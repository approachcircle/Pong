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
        Logger.info(log);
        return previous;
    }

    public void popTo(Class<? extends Screen> screen) {
        if (!containsTypeOf(screen)) {
            throw new IllegalStateException(String.format("screen '%s' not present in screen stack.", screen));
        }
        while (!isSameType(peek(), screen)) {
            pop();
        }
    }

    public boolean containsInstanceOf(Screen screen) {
        return stack.contains(screen);
    }

    public boolean containsTypeOf(Class<? extends Screen> screen) {
        for (Screen s : stack) {
            // this check is naive, but should do the trick
            // provided no two screens have the same class name
            if (isSameType(s, screen)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSameType(Screen first, Class<? extends Screen> second) {
        return first.getClass() == second;
    }

    public void push(Screen screen) {
        String log = String.format("screen pushed: %s -> %s", peek(), screen);
        inputManager.clearInputProcessors();
        stack.push(screen);
        Logger.info(log);
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
