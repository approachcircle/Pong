package net.approachcircle.game;

import javax.swing.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class UserFriendlyCrashHandler {
    private static JFrame frame;
    private static final int PADDING = 15;
    private static RuntimeException exception;

    public static void handle(RuntimeException e) {
        exception = e;
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        populateFrame();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void populateFrame() {
        SpringLayout layout = new SpringLayout();
        JLabel icon = new JLabel(new ImageIcon(getIconBytes()));
        JLabel heading = new JLabel("an error occurred and pong had to close! :(");
        heading.setFont(heading.getFont().deriveFont(Collections.singletonMap(TextAttribute.SIZE, 18)));
        JLabel error = new JLabel("<html><style>body {color: red;}</style><body width=385>" + exception.getClass().getSimpleName() + " (refer to the console for the full stacktrace)</body></html>");
        error.setFont(error.getFont().deriveFont(Collections.singletonMap(TextAttribute.WEIGHT, TextAttribute.WEIGHT_LIGHT)));
        JButton reveal = new JButton("click to reveal error information");
        reveal.addActionListener(event -> error.setVisible(true));
        layout.putConstraint(SpringLayout.WEST, icon, PADDING, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, icon, PADDING, SpringLayout.NORTH, frame);
        layout.putConstraint(SpringLayout.WEST, heading, PADDING, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, heading, PADDING, SpringLayout.SOUTH, icon);
        layout.putConstraint(SpringLayout.WEST, reveal, PADDING, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, reveal, PADDING, SpringLayout.SOUTH, heading);
        layout.putConstraint(SpringLayout.WEST, error, PADDING, SpringLayout.WEST, frame);
        layout.putConstraint(SpringLayout.NORTH, error, PADDING, SpringLayout.SOUTH, reveal);
        layout.putConstraint(SpringLayout.EAST, frame.getContentPane(), PADDING, SpringLayout.EAST, icon);
        layout.putConstraint(SpringLayout.SOUTH, frame.getContentPane(), PADDING, SpringLayout.SOUTH, error);
        frame.setLayout(layout);
        frame.add(icon);
        frame.add(heading);
        frame.add(reveal);
        frame.add(error);
        error.setVisible(false);
        frame.pack();
    }

    private static byte[] getIconBytes() {
        try (InputStream stream = Main.class.getClassLoader().getResourceAsStream("crash-icon-2.png")) {
            if (stream == null) {
                throw new RuntimeException("stream is null");
            }
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
