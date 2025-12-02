package org.cis1200.game2048;

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Run2048 implements Runnable {

    @Override
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("2048");
        frame.setLocationByPlatform(true);
        frame.setLayout(new BorderLayout());

        // Board UI
        final BoardUI boardUI = new BoardUI(500, 4);
        frame.add(boardUI, BorderLayout.CENTER);

        // Instructions Button
        final JButton instructionsButton = new JButton("How To Play");
        instructionsButton.setFocusable(false);
        instructionsButton.addActionListener(e -> {
            Component instructionsPanel = new InstructionsPanel();
            JOptionPane.showMessageDialog(
                    frame,
                    instructionsPanel,
                    "How To Play",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        JPanel instructionsPanel = new JPanel(); // make button centered and small
        instructionsPanel.add(instructionsButton);
        frame.add(instructionsPanel, BorderLayout.SOUTH);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static class InstructionsPanel extends JPanel {
        public InstructionsPanel() {
            super(new BorderLayout());
            setFocusable(false);
            String instructions = """
                    <html>
                    <b><u>About 2048:</b></u><br>
                    2048 is a classic tile merging game played on a 4x4 grid where the goal is<br>
                    to create a "2048" tile. Two tiles of the same numeric value can be<br>
                    merged into a single tile with double the previous value by lining them up<br>
                    and pushing all tiles in a direction that moves one tile into the other.<br>
                    Each move, a new tile is generated in one of the empty spaces on the board<br>
                    and is 2 (the lowest value) 90% of the time and 4 the other 10% of the time.<br>
                    <br>
                    <b><u>Controls:</b></u><br>
                    All you need are the four arrow keys (or WASD) to push tiles in any of the<br>
                    four cardinal directions: up, down, left, or right. You can undo your last<br>
                    move at any time by pressing the "undo" button. To reset the board<br>
                    entirely, press the "reset" button.
                    </html>
                    """;
            JLabel instructionsLabel = new JLabel(instructions);
            instructionsLabel.setFont(instructionsLabel.getFont().deriveFont(Font.PLAIN));
            add(instructionsLabel);
        }
    }
}
