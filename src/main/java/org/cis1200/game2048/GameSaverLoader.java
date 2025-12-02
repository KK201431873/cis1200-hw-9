package org.cis1200.game2048;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.StringTokenizer;

import org.cis1200.game2048.Board.BoardState;
import org.cis1200.game2048.Tile.State;

/**
 * Helper class to handle saving and loading game state.
 */
public class GameSaverLoader {

    private final File GAME_FILE;

    /**
     * Creates a new {@code GameSaverLoader} object with the given game file path.
     * @param filePath The destination game state file path.
     */
    public GameSaverLoader(String filePath) {
        try {
            GAME_FILE = new File(filePath);
            if (GAME_FILE.createNewFile()) {
                System.out.println("Created new game file at " + filePath);
            } else {
                System.out.println("Found game file at " + filePath);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file path");
        }
    }

    /**
     * Attempts to write the current game to the game file, with exception handling.
     * @param board The game's current {@code BoardState}.
     * @param boardHistory The past {@code BoardState}s.
     */
    public void saveGame(BoardState board, Stack<BoardState> boardHistory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GAME_FILE))) {
            // First write current board score and state
            writeBoardState(writer, board);

            // Then write the length of the board history
            writer.write(Integer.toString(boardHistory.size()));
            writer.newLine();
            writer.flush();

            // Then write each BoardState in boardHistory
            for (BoardState boardState : boardHistory) {
                writeBoardState(writer, boardState);
            }
        } catch (IOException e) {
            System.out.println("Failed to save game to " + GAME_FILE.getPath());
            e.printStackTrace();
        }
    }

    /**
     * Attempts to read the last game from the game file. If the file is corrupted, create a new
     * game of grid size 4.
     * @return The last (or new) 2048 game.
     */
    public Board loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(GAME_FILE))) {
            // Read current board score and state
            BoardState curBoard = readBoardState(reader);

            // Read length of board history
            StringTokenizer st = new StringTokenizer(reader.readLine());
            int boardHistorySize = Integer.parseInt(st.nextToken());
            Stack<BoardState> boardHistory = new Stack<>();
            for (int i = 0; i < boardHistorySize; i++) {
                boardHistory.add(readBoardState(reader));
            }

            System.out.println("Loaded previous game.");
            return new Board(curBoard, boardHistory);
        } catch (IOException | NullPointerException | NoSuchElementException e) {
            System.out.println("Created new game.");
            return new Board(4);
        }
    }

    /**
     * Helper method for writing a board state and score using the given {@code BufferedWriter}.
     * @param writer A valid {@code BufferedWriter}.
     * @param board The {@code BoardState} to write.
     */
    private void writeBoardState(BufferedWriter writer, BoardState board) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("Writer is null");
        }

        // Write "[score] [grid size]", both as ints
        StringBuilder sb = new StringBuilder();
        sb.append(board.getScore()).append(" ").append(board.getN());
        writer.write(sb.toString());
        writer.newLine();
        writer.flush();

        // Write the ordinals of each entry in the grid. For empty tiles (null), write -1, e.g.
        // -1  0  0 -1
        // -1 -1 -1 -1
        //  1 -1  2 -1
        // -1 -1 -1  0
        State[][] curTiles = board.asArray();
        sb = new StringBuilder();
        for (State[] row : curTiles) {
            for (State tile : row) {
                if (tile == null) {
                    sb.append(-1).append(" ");
                } else {
                    sb.append(tile.ordinal()).append(" ");
                }
            }
            sb.append("\n");
        }
        writer.write(sb.toString());
        writer.flush();
    }

    private BoardState readBoardState(BufferedReader reader) throws IOException {
        // Read score and grid size as ints
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int score = Integer.parseInt(st.nextToken());
        int gridSize = Integer.parseInt(st.nextToken());

        // Read board
        State[][] tiles = new  State[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            st = new StringTokenizer(reader.readLine());
            for (int j = 0; j < gridSize; j++) {
                int tileOrdinal = Integer.parseInt(st.nextToken());
                if (tileOrdinal >= 0) {
                    tiles[i][j] = State.values()[tileOrdinal];
                }
            }
        }

        return new BoardState(tiles, score);
    }


}
