package net.approachcircle.game.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationGroup extends Renderable {
    private final List<Notification> group;

    public NotificationGroup() {
        group = new ArrayList<>();
    }

    public void remove(Notification notification) {
        if (group.isEmpty()) {
            return;
        }
        logRemoval(notification);
        group.remove(notification);
    }

    private void logRemoval(Notification removed) {
        String log = String.format("notification removed: %s -> %d items", removed, group.size() - 1);
        Logger.info(log);
    }

    public void removeLast() {
        logRemoval(group.get(group.size() - 1));
        remove(group.size() - 1);
    }

    public void remove(int index) {
        logRemoval(group.get(index));
        remove(group.get(index));
    }

    public void add(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("adding a null notification?");
        }
        // only one error notification can be displayed at once, but multiple dialog boxes can be displayed
        // on top of one another
        if (!(notification instanceof DialogBox)) {
            for (Notification current : group) {
                if (!(current instanceof DialogBox)) {
                    current.suspend();
                }
            }
        }
        group.add(notification);
        String log = String.format("notification added: %s -> %d items", notification, group.size());
        Logger.info(log);
    }

    public void insert(int index, Notification notification) {
        group.add(index, notification);
    }

    public boolean isEmpty() {
        return group.isEmpty();
    }

    public void clear() {
        group.clear();
    }

    @Override
    public void render() {
        for (Iterator<Notification> iterator = group.iterator(); iterator.hasNext();) {
            Notification notification = iterator.next();
            if (!notification.isAlive()) {
                for (Notification n : group) {
                    if (n.isSuspended()) {
                        n.awake();
                    }
                }
                logRemoval(notification);
                iterator.remove();
                continue;
            }
            if (!notification.isSuspended()) {
                notification.render();
            }
        }
    }
}
