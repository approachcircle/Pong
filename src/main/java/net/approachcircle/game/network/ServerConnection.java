package net.approachcircle.game.network;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import net.approachcircle.game.Game;
import net.approachcircle.game.backend.ErrorNotification;
import net.approachcircle.game.backend.Logger;
import org.apache.commons.lang3.time.StopWatch;

public class ServerConnection {
    private static ServerConnection instance;
    private final URI uri;
    private final Socket socket;
    private volatile ServerResponse lastResponse;
    private GameEvent lastEvent;
    private int ackTimeout = 2000;
    private boolean ackCancelled = false;
    private int connectionFailures = 0;

    private ServerConnection() {
        try {
            uri = new URI("ws://localhost:1909");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        IO.Options options = IO.Options.builder()
                .setReconnection(true)
                .setReconnectionAttempts(Integer.MAX_VALUE) // try forever (basically)
                .setReconnectionDelay(10000) // every 10 seconds
                .setTimeout(10000) // timeout after 10 seconds of no response
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

    public void setAckTimeout(int milliseconds) {
        ackTimeout = milliseconds;
    }

    /**
     * calls {@code emitEventAsynchronously()} then calls {@code awaitAndConsumeResponse()} (provided the
     * {@code GameEvent} is expecting an acknowledgement) to spin until the server responds.
     * @param event the {@code GameEvent} to emit to the server
     * @param argsTo the args to emit to the server alongside the {@code GameEvent}
     * @return the {@code ServerResponse} object containing information from the server
     */
    public ServerResponse emitEventSynchronously(GameEvent event, Object... argsTo) {
        emitEventAsynchronously(event, argsTo);
        if (!event.isExpectingAck()) { return null; }
        return awaitAndConsumeResponse();
    }

    public void emitEventAsynchronously(GameEvent event, Object... argsTo) {
        Logger.info(String.format("-> event '%s' outgoing", event.name())); // ensure this filters player movement events
        lastEvent = event;
        socket.emit(event.getRawValue(), argsTo, argsFrom -> {
            lastEvent = null;
            Logger.info(String.format("<- ack received for '%s'", event.name()));
            if (ackCancelled) {
                ackCancelled = false;
                return;
            }
            ServerResponse response = new ServerResponse();
            if (argsFrom.length != 2) { // malformed response
                response.state = State.Error;
                response.message = "server response was malformed";
                return;
            }
            try {
                response.state = State.valueOf((String) argsFrom[0]);
            } catch (IllegalArgumentException e) {
                response.state = State.Error;
                response.message = "server response contained invalid state";
                return;
            }
            response.message = (String) argsFrom[1];
            lastResponse = response;
        });
    }

    /**
     * <p>
     *     spins and blocks thread execution until either the server's response is no longer null,
     *     or the timeout period (set by {@code setAckTimeout()}) has been reached.
     * </p>
     * <p>
     *     <b>
     *         WARNING: this method is guaranteed to spin until the timeout period if the
     *         {@code GameEvent} is not expecting an acknowledgement.
     *     </b>
     * </p>
     * @return the {@code ServerResponse} object containing information from the server
     */
    public ServerResponse awaitAndConsumeResponse() {
        StopWatch sw = new StopWatch();
        sw.start();
        while (lastResponse == null) {
            if (sw.getTime(TimeUnit.MILLISECONDS) > ackTimeout) {
                Logger.error(String.format("ack timed out for event '%s'", lastEvent.name()));
                Game.getInstance().getNotificationGroup().add(new ErrorNotification("server did not respond"));
                ackCancelled = true;
                ServerResponse response = new ServerResponse();
                response.state = State.Error;
                response.message = "server failed to send acknowledgement";
                sw.stop();
                return response;
            }
            Thread.onSpinWait();
        }
        return consumeResponse();
    }

    public ServerResponse consumeResponse() {
        ServerResponse response = lastResponse;
        lastResponse = null;
        return response;
    }

    public void connect() {
        socket.connect();
        socket.on("connect_error", (args) -> {
            //TODO: add a reconnection timer in the main menu e.g. "connection failed, retrying in x seconds"
            connectionFailures++;
            if (connectionFailures == 1 || connectionFailures % 5 == 0) {
                Logger.error(String.format("[fail #%d] network error: %s", connectionFailures, ((Exception) args[0]).getMessage()));
            }
        });
        socket.on("connect", (args) -> {
            connectionFailures = 0;
            Logger.info("connected to server!");
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
