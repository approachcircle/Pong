package net.approachcircle.game;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.net.URISyntaxException;

public class ServerConnection {
    private static ServerConnection instance;
    private final URI uri;
    private final Socket socket;

    private ServerConnection() {
        try {
            uri = new URI("ws://localhost:1909");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        IO.Options options = IO.Options.builder()
                .setReconnection(true)
                .setReconnectionAttempts(Integer.MAX_VALUE)
                .setReconnectionDelay(5000)
                .setTimeout(10000)
                .setForceNew(true)
                .build();
        socket = IO.socket(uri, options);
    }

    public static ServerConnection getInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
        return instance;
    }

    public void connect() {
        socket.connect();
        socket.on("connect_error", (args) -> {
            System.out.printf("network error: %s%n", ((Exception) args[0]).getMessage());
        });
    }

    public void close() {
        socket.close();
    }

    public ConnectionState getState() {
        if (socket.connected()) {
            return ConnectionState.Online;
        } else {
            return ConnectionState.Offline;
        }
    }
}
