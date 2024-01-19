package net.approachcircle.game.network;

public enum GameEvent {
    CREATE_GAME("create-game"),
    JOIN_GAME("join-game"),
    LEAVE_GAME("leave-game");
    private final String rawValue;
    private final boolean expectingAck;

    GameEvent(String rawValue, boolean expectingAck) {
        this.rawValue = rawValue;
        this.expectingAck = expectingAck;
    }
    GameEvent(String rawValue) { this(rawValue, true); }
    public String getRawValue() {
        return rawValue;
    }
    public boolean isExpectingAck() { return expectingAck; }
}
