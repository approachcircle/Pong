package net.approachcircle.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import net.approachcircle.game.backend.*;
import net.approachcircle.game.network.ServerConnection;


public class Game extends ApplicationAdapter implements ScreenManager, NotificationManager, InputManager {
    public static Game instance;
    private Crosshair crosshair;
    private InputMultiplexer inputMultiplexer;
    private ScreenStack screenStack;
    private DiscordPresence discord;
    private NotificationStack notificationStack;

    @Override
    public void create() {
        ServerConnection.getInstance().connect();
        inputMultiplexer = new InputMultiplexer();
        screenStack = new ScreenStack(this);
        notificationStack = new NotificationStack(this);
        discord = new DiscordPresence();
        Gdx.input.setInputProcessor(inputMultiplexer);
        screenStack.push(new MainMenuScreen());
        crosshair = new Crosshair();
        discord.connect();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (screenStack.isEmpty()) {
                Gdx.app.exit();
            }
            switch (getScreenStack().peek().getEscapeBehaviour()) {
                case Exit -> Gdx.app.exit();
                case Pop -> getScreenStack().pop();
                case Other -> {}
                default -> throw new EnumConstantNotPresentException(EscapeBehaviour.class, getScreenStack().peek().getEscapeBehaviour().toString());
            }
        }
        if (!screenStack.isEmpty()) {
            screenStack.peek().render();
        }
        if (!notificationStack.isEmpty()) {
            if (!notificationStack.peek().isAlive()) {
                notificationStack.pop();
            }
        }
        if (!notificationStack.isEmpty()) {
            notificationStack.peek().render();
        }
        crosshair.render();
        discord.update(getDiscordState());
    }

    private String getDiscordState() {
        if (screenStack.peek() instanceof MainMenuScreen) {
            return "In the main menu";
        } else if (screenStack.peek() instanceof DifficultySelectScreen) {
            return "Choosing a difficulty";
        } else if (screenStack.peek() instanceof PongScreen) {
            return "In a game";
        } else if (screenStack.peek() instanceof PauseScreen) {
            return "In a paused game";
        } else if (screenStack.peek() instanceof ResultScreen) {
            return "Finished a game";
        } else if (screenStack.peek() instanceof MultiplayerCreationScreen) {
            return "Creating/joining a multiplayer game";
        }
        return "In the " + screenStack.peek().toString();
    }

    @Override
    public void dispose() {
        Logger.info("disposing!");
        instance = null;
        discord.disconnect();
        ServerConnection.getInstance().close();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    @Override
    public ScreenStack getScreenStack() {
        return screenStack;
    }

    @Override
    public void addInputProcessor(InputProcessor processor) {
        if (inputMultiplexer == null) {
            inputMultiplexer = new InputMultiplexer();
        }
        inputMultiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void removeInputProcessor(InputProcessor processor) {
        inputMultiplexer.removeProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void clearInputProcessors() {
        inputMultiplexer.clear();
    }

    @Override
    public NotificationStack getNotificationStack() {
        return notificationStack;
    }
}
