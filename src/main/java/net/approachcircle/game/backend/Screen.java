package net.approachcircle.game.backend;

import java.util.*;

public abstract class Screen extends Renderable {
    private final List<Renderable> members = new ArrayList<>();

    public EscapeBehaviour getEscapeBehaviour() {
        return EscapeBehaviour.Other;
    }
    public void onEnter() {}
    public void onExit() {}

    /**
     * <p>
     *     adds a {@code Renderable} to the back of the members list.
     * </p>
     * <b>
     *     the last member in the list will be rendered last, meaning it will be rendered top-most.
     * </b>
     * @param renderable the {@code Renderable} to add to the member list.
     */
    public void addMember(Renderable renderable) {
        members.add(renderable);
    }

    /**
     * <p>
     *     removes a {@code Renderable} from the members list.
     * </p>
     * @param renderable the {@code Renderable} to remove from the members list.
     */
    public void removeMember(Renderable renderable) {
        members.remove(renderable);
    }
    public void logCurrentMembers() {
        Logger.info(String.format("current members in screen '%s': ", this));
        int i = 0;
        for (Renderable renderable : members) {
            Logger.info(String.format("%d: %s", i, renderable));
            i++;
        }
    }

    /**
     * this method is responsible for iterating through each member in this {@code Screen} and rendering it. after all
     * members have been rendered for this frame, the {@code update()} method of this {@code Screen} is called. this
     * method rarely needs to be overridden unless the core rendering functionality of this entire {@code Screen}
     * needs to be changed.
     */
    @Override
    public void render() {
        for (Renderable renderable : members) {
            if (renderable.isVisible()) {
                renderable.render();
            }
        }
        update();
    }

    /**
     * this method will be called every frame immediately after this {@code Screen}'s {@code Renderable}s are
     * rendered. override this for any logic that needs to be executed every frame. in most cases, this excludes
     * explicit rendering of a {@code Renderable} as this is done implicitly on each member every frame anyway.
     */
    public void update() {}
}
