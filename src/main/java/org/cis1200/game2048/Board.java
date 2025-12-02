package org.cis1200.game2048;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * This class contains the headless logic of the 2048 game, like as tile generation and merging.
 */
public class Board {

    private BoardState board;
    private final Stack<BoardState> boardHistory;
    private final int n;

    /**
     * Create a new n by n 2048 game board with the specified side length. Randomly generates two
     * tiles to start the player off with.
     *
     * @param n Side length of the board (number of tiles).
     */
    public Board(int n) {
        this.board = new BoardState(n);
        this.boardHistory = new Stack<>();
        this.n = n;

        // generate two tiles from the start
        generateNewTile();
        generateNewTile();
    }

    /**
     * Constructs a new Board using the given score and array of {@code Tile.State}s. Throws
     * {@code IllegalArgumentException} if:
     * <ol>
     *    <li>{@code tiles} is null,</li>
     *    <li>{@code tiles} is not square, or</li>
     *    <li>{@code tiles} has side length less than 4.</li>
     * </ol>
     * @param tiles A 2D array of {@code Tile.State}s.
     * @param score The initial score
     */
    public Board(Tile.State[][] tiles, int score) {
        this.board = new BoardState(tiles, score);
        this.boardHistory = new Stack<>();
        this.n = this.board.getN();
    }

    /**
     * Constructs a new Board using the given array of {@code Tile.State}s. Throws
     * {@code IllegalArgumentException} if:
     * <ol>
     *    <li>{@code tiles} is null,</li>
     *    <li>{@code tiles} is not square, or</li>
     *    <li>{@code tiles} has side length less than 4.</li>
     * </ol>
     * @param tiles A 2D array of {@code Tile.State}s.
     */
    public Board(Tile.State[][] tiles) {
        this(tiles, 0);
    }

    /**
     * Constructs a new Board using the given current {@code BoardState} and
     * {@code Stack<BoardState>} board history.
     * @param curBoard The current board state.
     * @param boardHistory The past board states.
     */
    public Board(BoardState curBoard, Stack<BoardState> boardHistory) {
        this.board = curBoard;
        this.boardHistory = boardHistory;
        this.n = this.board.getN();
    }

    /**
     * Clears this board's tiles and score and generates two random tiles.
     */
    public void reset() {
        this.board = new BoardState(n);
        this.boardHistory.clear();

        // generate two tiles from the start
        generateNewTile();
        generateNewTile();
    }

    /**
     * Undoes the last move, if there are moves to undo.
     * @return Whether any moves were undone.
     */
    public boolean undoMove() {
        if (!boardHistory.isEmpty()) {
            this.board = boardHistory.pop();
            return true;
        }
        return false;
    }

    public void printBoardHistory() {
        System.out.println("=== BOARD HISTORY ===");
        for (BoardState boardState : boardHistory) {
            System.out.println(boardState);
        }
        System.out.println("=====================");
    }

    /**
     * @return The current score of this board.
     */
    public int getScore() {
        return this.board.getScore();
    }

    /**
     * Getter method for testing/debugging purposes.
     * @return The length of this board's history.
     */
    public int getBoardHistoryLength() {
        return this.boardHistory.size();
    }

    /**
     * Getter method for testing/debugging purposes ONLY.
     * @return This board's history.
     */
    public Stack<BoardState> getBoardHistory() {
        return this.boardHistory;
    }

    /**
     * Getter method for testing/debugging purposes.
     * @return The current BoardState as a 2D array of {@code Tile.State}s.
     */
    public Tile.State[][] getBoardState() {
        return this.board.asArray();
    }

    /**
     * Attempt to save the current game to the game state file.
     * @param gameSaverLoader A valid {@code GameSaverLoader} object.
     */
    public void saveGame(GameSaverLoader gameSaverLoader) {
        gameSaverLoader.saveGame(board, boardHistory);
    }

    /**
     * Attempts to generate a new random tile with a 90% chance of being 2 and 10% chance of
     * being 4.
     */
    public void generateNewTile() {
        // Check if board is full
        if (board.getFull()) {
            return;
        }

        // Pick random tile value with 0 < x < 1
        // x < 0.9 -> value 2
        // x > 0.9 -> value 4
        Tile.State newTileState;
        if (Math.random() < 0.9) {
            newTileState = Tile.State.MERGE0;
        } else {
            newTileState = Tile.State.MERGE1;
        }
        Tile newTile = new Tile(newTileState);

        // Replace random empty tile
        List<int[]> emptyTiles = board.getEmptyTiles();
        int tileIndex = (int) (emptyTiles.size() * Math.random());
        int row = emptyTiles.get(tileIndex)[0];
        int col = emptyTiles.get(tileIndex)[1];
        board.setTile(row, col, newTile);
    }

    /**
     * Attempts to shift and merge the tiles upward.
     * @return Whether the move changed the state of the board.
     */
    public boolean moveUp() {
        return moveDirection(MoveDirection.UP);
    }

    /**
     * Attempts to shift and merge the tiles downward.
     * @return Whether the move changed the state of the board.
     */
    public boolean moveDown() {
        return moveDirection(MoveDirection.DOWN);
    }

    /**
     * Attempts to shift and merge the tiles to the left.
     * @return Whether the move changed the state of the board.
     */
    public boolean moveLeft() {
        return moveDirection(MoveDirection.LEFT);
    }

    /**
     * Attempts to shift and merge the tiles to the right.
     * @return Whether the move changed the state of the board.
     */
    public boolean moveRight() {
        return moveDirection(MoveDirection.RIGHT);
    }

    /**
     * Helper function to both save previous board state into history and then move the tiles.
     * @param direction The direction to move the tiles in.
     * @return Whether the move changed the state of the board.
     */
    private boolean moveDirection(MoveDirection direction) {
        BoardState oldBoard = new BoardState(board.asArray(), board.getScore());
        boolean boardChanged = board.move(direction);
        if (boardChanged) {
            boardHistory.add(oldBoard);
        }
        return boardChanged;
    }

    /**
     * @return Whether the player has won, lost, or is still playing as a {@code GameStatus}
     * enum object.
     */
    public GameStatus getGameStatus() {
        return board.getGameStatus();
    }

    @Override
    public String toString() {
        return board.toString();
    }


    /**
     * Represents a player command to shift and merge the board in a given direction. Contains
     * the direction (d_row, d_col) for calculations.
     */
    public enum MoveDirection {
        UP(-1,0),
        DOWN(1,0),
        LEFT(0,-1),
        RIGHT(0,1);

        public final int dr;
        public final int dc;

        MoveDirection(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
    }

    public enum GameStatus {
        PLAYING(),
        LOST(),
        WON();
        GameStatus() {
        }
    }

    /**
     * Contains an encapsulated array of tiles and points representing the state of the board.
     */
    public static class BoardState {
        private final Tile[][] tiles;
        private final int n;
        private int score;

        /**
         * Create a new n by n board state with the specified side length. Throws
         * {@code IllegalArgumentException} if n is less than 4.
         *
         * @param n Side length of the board (number of tiles).
         */
        public BoardState(int n) {
            if (n < 4) {
                throw new IllegalArgumentException("n must be at least 4.");
            }
            this.n = n;
            this.tiles = new Tile[n][n];
            this.score = 0;
        }

        /**
         * Constructs a new BoardState using the given array of {@code Tile.State}s. Throws
         * {@code IllegalArgumentException} if:
         * <ol>
         *    <li>{@code tiles} is null,</li>
         *    <li>{@code tiles} is not square, or</li>
         *    <li>{@code tiles} has side length less than 4.</li>
         * </ol>
         * @param tiles A 2D array of {@code Tile.State}s.
         */
        public BoardState(Tile.State[][] tiles, int score) {
            if (tiles == null) {
                throw new IllegalArgumentException("Array is null.");
            }
            // Check square & min size
            this.n = tiles.length;
            if (n < 4) {
                throw new IllegalArgumentException("Array length must be at least 4.");
            }
            this.tiles = new Tile[n][n];

            // copy over input
            for (int i = 0; i < n; i++) {
                Tile.State[] row = tiles[i];
                if (row == null || row.length != n) {
                    throw new IllegalArgumentException("Array must be square.");
                }
                for (int j = 0; j < n; j++) {
                    Tile.State tile = row[j];
                    if (tile != null) {
                        this.tiles[i][j] = new Tile(tile);
                    }
                }
            }

            this.score = score;
        }

        /**
         * @return This {@code BoardState}'s grid size in number of tiles.
         */
        public int getN() {
            return n;
        }

        /**
         * @return Whether every tile in {@code this.tiles} is non-null.
         */
        public boolean getFull() {
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    if (tile == null) {
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * @return A list of [row, col] arrays representing the coordinates of empty tiles in
         * this board.
         */
        public List<int[]> getEmptyTiles() {
            List<int[]> ret = new LinkedList<>();
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (tiles[row][col] == null) {
                        ret.add(new int[]{row, col});
                    }
                }
            }
            return ret;
        }

        /**
         * If the tile at the given coordinates is empty, replace that empty tile with the given
         * tile. Throws {@code IllegalArgumentException} if {@code row} or {@code col} are out of
         * bounds, if {@code tile} is null, or if the specified tile is non-empty.
         * @param row Row coordinate of the empty tile.
         * @param col Column coordinate of the empty tile.
         * @param tile The new tile.
         */
        public void setTile(int row, int col, Tile tile) {
            if (row < 0 || row >= n || col < 0 || col >= n) {
                throw new IllegalArgumentException("Row or column out of bounds.");
            }
            if (tile == null) {
                throw new IllegalArgumentException("Tile is null.");
            }
            if (tiles[row][col] != null) {
                throw new IllegalArgumentException("Tile is non-empty.");
            }
            tiles[row][col] = tile;
        }

        /**
         * @return The tiles on this board represented as a 2D array of {@code Tile.State}s.
         */
        public Tile.State[][] asArray() {
            Tile.State[][] ret = new Tile.State[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Tile tile = tiles[i][j];
                    if (tile != null) {
                        ret[i][j] = tile.getState();
                    }
                }
            }
            return ret;
        }

        /**
         * Increment the score by the given number of points.
         * @param points The number of points.
         */
        public void incrementScore(int points) {
            this.score += points;
        }

        /**
         * Increment the score by the given tile's value.
         * @param tile The given tile.
         */
        public void incrementScore(Tile tile) {
            incrementScore(tile.getState().getPoints());
        }

        /**
         * @return The total number of points accumulated.
         */
        public int getScore() {
            return this.score;
        }

        /**
         * Attempts to shift and merge the tiles in the board in the given direction.
         * @param direction The direction to move.
         * @return Whether the move changed the state of the board.
         */
        public boolean move(MoveDirection direction) {
            int startRow = Math.max(0, (n - 1) * direction.dr);
            int startCol = Math.max(0, (n - 1) * direction.dc);

            int shiftRow = Math.abs(direction.dc);
            int shiftCol = Math.abs(direction.dr);

            // process each line (perpendicular to move direction)
            boolean boardChanged = false;
            for (int i = 0; i < n; i++) {
                // get start or end coordinates of the line
                int lineRow = startRow + i * shiftRow;
                int lineCol = startCol + i * shiftCol;
                int lastTileIndex = -1;
                Tile lastTile = null;
                // start from end of line and work backwards, checking mergeability
                for (int j = 0; j < n; j++) {
                    int row = lineRow - j * direction.dr;
                    int col = lineCol - j * direction.dc;
                    Tile cur = tiles[row][col];
                    if (cur == null) {
                        continue;
                    }

                    // compare current tile against last nonnull tile
                    if (cur.equals(lastTile)) {
                        // merge tiles
                        lastTile.upgrade();
                        tiles[row][col] = null; // delete merged tile
                        // increment points
                        incrementScore(lastTile);
                        // don't merge this tile again
                        lastTile = null;
                    } else {
                        // move tile over
                        int newIndex = lastTileIndex + 1;
                        lastTileIndex = newIndex;
                        lastTile = cur;
                        if (newIndex == j) {
                            continue;
                        }
                        int newRow = lineRow - newIndex * direction.dr;
                        int newCol = lineCol - newIndex * direction.dc;
                        tiles[row][col] = null;
                        tiles[newRow][newCol] = cur;
                    }
                    boardChanged = true;
                }
            }
            return boardChanged;
        }

        /**
         * @return Whether the player has won, lost, or is still playing as a {@code GameStatus}
         * enum object.
         */
        public GameStatus getGameStatus() {
            BoardState copy = new BoardState(asArray(), getScore());

            // check won
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    if (tile != null && tile.getState() == Tile.State.MERGE10) {
                        return GameStatus.WON;
                    }
                }
            }

            // check lost
            if (!copy.move(MoveDirection.UP) &&
                !copy.move(MoveDirection.DOWN) &&
                !copy.move(MoveDirection.LEFT) &&
                !copy.move(MoveDirection.RIGHT)) {
                return GameStatus.LOST;
            }

            return GameStatus.PLAYING;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Score: ").append(score).append("\n");
            for (Tile[] row : tiles) {
                for (Tile tile : row) {
                    int points = 0;
                    if (tile != null) {
                        points = tile.getState().getPoints();
                    }
                    sb.append(String.format("%-5s", points));
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
