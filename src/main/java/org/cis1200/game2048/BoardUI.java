package org.cis1200.game2048;

import org.cis1200.game2048.Tile.State;
import org.cis1200.game2048.Board.GameStatus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class holds the BoardUI, score label, and button widgets.
 */
public class BoardUI extends JPanel {

    private final int SIDE_LENGTH;
    private final int GRID_SIZE;
    private final Board board;
    private final JLabel scoreLabel, gameStatusLabel;
    private final GameSaverLoader gameSaverLoader;

    /**
     * Creates a new 2048 Board UI object with the given side length and grid size.
     * @param sideLength Side length of the entire board in pixels.
     * @param gridSize Number of tiles along each side of the board (e.g. 4 means a 4x4 grid).
     */
    public BoardUI(int sideLength, int gridSize) {
        this.SIDE_LENGTH = sideLength;
        this.GRID_SIZE = gridSize;
        this.gameSaverLoader = new GameSaverLoader("files/game_state.txt");
        this.board = gameSaverLoader.loadGame();
        saveGame();

        /// Format this JPanel
        setFocusable(true);
        setLayout(new BorderLayout());

        /// Status bar
        final JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setFocusable(false);
        add(statusBar, BorderLayout.NORTH);

        // score
        scoreLabel = new JLabel("Score: 0") {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 0);
            }
        };
        scoreLabel.setFocusable(false);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        statusBar.add(scoreLabel, BorderLayout.WEST);

        // undo
        final JButton undoButton = new JButton("Undo Move");
        undoButton.setFocusable(false);
        undoButton.addActionListener(e -> {
            if (board.undoMove()) {
                saveGame();
                repaint();
            } else {
                showInfoWindow("Undo Move", "No moves to undo.");
            }
        });
        final JPanel undoButtonPanel = new JPanel();
        undoButtonPanel.add(undoButton);
        statusBar.add(undoButtonPanel, BorderLayout.CENTER);

        // reset
        final JButton resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> {
            board.reset();
            saveGame();
            repaint();
        });
        final JPanel resetButtonPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 0);
            }
        };
        resetButtonPanel.add(resetButton);
        statusBar.add(resetButtonPanel, BorderLayout.EAST);

        // Game status label
        JPanel statusLabelPanel = new JPanel();
        gameStatusLabel = new JLabel("Loading Game...");
        gameStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gameStatusLabel.setFocusable(false);
        gameStatusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gameStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabelPanel.add(gameStatusLabel);
        statusBar.add(statusLabelPanel, BorderLayout.SOUTH);

        /// Game board
        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boardPaintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(SIDE_LENGTH, SIDE_LENGTH);
            }
        };
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boardPanel.setFocusable(true);
        add(boardPanel, BorderLayout.CENTER);

        // Keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_D:
                        break;
                    default: // not a directional key
                        return;
                }

                if (checkGameStatus(true)) {
                    return;
                }

                // try performing move
                boolean boardUpdated = false;
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    boardUpdated = board.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    boardUpdated = board.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
                    boardUpdated = board.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
                    boardUpdated = board.moveUp();
                }
                if (boardUpdated) {
                    board.generateNewTile();
                    saveGame();
                    repaint();
                    checkGameStatus(true);
                }

            }
        });
    }

    /**
     * Helper function to check whether the user has won or lost and display a popup message.
     * @param showInfoWindow Whether to show the popup window.
     * @return Whether the user has won or lost.
     */
    private boolean checkGameStatus(boolean showInfoWindow) {
        GameStatus gameStatus = board.getGameStatus();
        if (gameStatus == GameStatus.WON) {
            gameStatusLabel.setText("You Won!");
            if (showInfoWindow) {
                showInfoWindow("You Won!", "You got the 2048 tile!");
            }
            return true;
        }
        if (gameStatus == GameStatus.LOST) {
            gameStatusLabel.setText("You Lost!");
            if (showInfoWindow) {
                showInfoWindow("You Lost!", "There are no legal moves left.");
            }
            return true;
        }
        gameStatusLabel.setText("Playing");
        return false;
    }

    /**
     * Helper method to show an info window to the user.
     * @param title The title of the info window.
     * @param text The text content of the info window.
     */
    private void showInfoWindow(String title, String text) {
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.add(new JLabel(text));
        JOptionPane.showMessageDialog(
                this,
                instructionsPanel,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Helper function for saving the game to the game state file.
     */
    private void saveGame() {
        board.saveGame(gameSaverLoader);
    }

    public void boardPaintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // enable antialiasing for smooth looking shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // update score label
        scoreLabel.setText("Score: " + board.getScore());

        // update game status label
        checkGameStatus(false);

        // draw background/outline
        g2d.setColor(Color.decode("#bfaba3"));
        g2d.fillRect(0, 0, SIDE_LENGTH, SIDE_LENGTH);

        // draw tiles
        State[][] boardState = board.getBoardState();
        int gridSideLength = (int) ((double)SIDE_LENGTH / GRID_SIZE);
        int tileMargin = gridSideLength / 20;
        int tileLength = gridSideLength - 2 * tileMargin;
        int roundRectRadius = tileLength / 10;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // draw tile box
                int x1 = j * gridSideLength;
                int y1 = i * gridSideLength;

                // draw tile point value
                State tile = boardState[i][j];
                if (tile != null) {
                    int cx = x1 + (int) (gridSideLength / 2.0);
                    int cy = y1 + (int) (gridSideLength / 2.0);

                    // draw tile background
                    g2d.setColor(tile.getBgColor());
                    g2d.fillRoundRect(
                            x1 + tileMargin,
                            y1 + tileMargin,
                            tileLength,
                            tileLength,
                            roundRectRadius,
                            roundRectRadius
                    );

                    // draw tile value (centered, adjusted text width)
                    g2d.setColor(tile.getTextColor());
                    String s = Integer.toString(tile.getPoints());
                    g2d.setFont(new Font(
                            "Arial",
                            Font.BOLD,
                            52 - 4*Math.max(0, s.length()-1)
                    ));
                    FontMetrics fm = g2d.getFontMetrics();
                    g2d.drawString(
                            s,
                            cx - fm.stringWidth(s)/2,
                            cy - fm.getHeight()/2 + fm.getAscent()
                    );
                } else {
                    // draw empty background
                    g2d.setColor(Color.decode("#ccc1b3"));
                    g2d.fillRoundRect(
                            x1 + tileMargin,
                            y1 + tileMargin,
                            tileLength,
                            tileLength,
                            roundRectRadius,
                            roundRectRadius
                    );
                }
            }
        }
    }

}
