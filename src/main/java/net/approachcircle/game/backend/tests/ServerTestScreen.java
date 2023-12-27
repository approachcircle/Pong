package net.approachcircle.game.backend.tests;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import net.approachcircle.game.backend.Screen;
import net.approachcircle.game.backend.ShapeRenderable;

import java.net.URISyntaxException;

public class ServerTestScreen extends Screen {
    Socket socket;
    private float x;
    private final float step = 5;
    ShapeRenderable square;
    public ServerTestScreen() {
        x = 0;
        square = new ShapeRenderable() {
            @Override
            public void render() {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rect(x, 0, 50, 50);
                shapeRenderer.end();
            }
        };
        try {
            socket = IO.socket("ws://localhost:1707");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        socket.connect();
    }

    @Override
    public void render() {
        socket.emit("move", x + step, (Ack) args -> {
            if ((boolean)args[0]) {
                x += step;
                System.out.println("accepted");
            } else {
                System.out.println("rejected");
            }
        });
        square.render();
    }
}
