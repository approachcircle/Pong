package net.approachcircle.game.backend;

import java.util.Stack;

public class ScreenStack {
    private final Stack<Screen> stack;
    public ScreenStack() {
        stack = new Stack<>();
    }

    public Screen pop() {
        return stack.pop();
    }

    public void push(Screen screen) {
        stack.push(screen);
    }

    public Screen peek() {
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
