package net.approachcircle.game;

import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;

public class DiscordPrescence {
    private final long applicationID = 1192161452287590420L;
    private final RichPresence rpc;
    public DiscordPrescence() {
        rpc = new RichPresence();
    }

    public void connect() {
        DiscordIPC.start(applicationID, null);
        rpc.setStart(System.currentTimeMillis() / 1000L);
        rpc.setLargeImage("pong-icon", "Pong");
    }

    public void update(String state) {
        rpc.setDetails("Playing pong");
        rpc.setState(state);
        DiscordIPC.setActivity(rpc);
    }

    public void disconnect() {
        DiscordIPC.stop();
    }
}
