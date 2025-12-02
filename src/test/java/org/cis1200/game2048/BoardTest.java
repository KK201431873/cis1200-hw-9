package org.cis1200.game2048;

import org.cis1200.game2048.Tile.State;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Arrays;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the core functionalities of Board.java
 */
public class BoardTest {

    @Test
    public void testInitGenerateTiles() {
        Board board = new Board(4);
        System.out.println(board);

        // count empty tiles
        Tile.State[][] boardState = board.getBoardState();
        int numEmptyTiles = 0;
        for (Tile.State[] row : boardState) {
            for (Tile.State tile : row) {
                if (tile == null) {
                    numEmptyTiles++;
                }
            }
        }
        assertEquals(
                14,
                numEmptyTiles,
                "Test board creation generates two random tiles"
        );
    }

    @Test
    public void testMoveUp() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });
        System.out.println(board);

        assertTrue(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE1,         null },
                {         null,         null, State.MERGE1,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp 1");

        assertTrue(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE2,         null },
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp 2");

        assertTrue(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
                { State.MERGE0,         null,         null,         null },
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp 3");

        assertFalse(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
                { State.MERGE0,         null,         null,         null },
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp doesn't change");
    }


    @Test
    public void testMoveDown() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });
        System.out.println(board);

        assertTrue(board.moveDown());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE1,         null },
                { State.MERGE1, State.MERGE0, State.MERGE1,         null },
        }, board.getBoardState(), "test moveDown 1");

        assertTrue(board.moveDown());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
        }, board.getBoardState(), "test moveDown 2");

        assertTrue(board.moveDown());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
        }, board.getBoardState(), "test moveDown 3");

        assertFalse(board.moveDown());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
        }, board.getBoardState(), "test moveDown doesn't change");
    }

    @Test
    public void testMoveLeft() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });
        System.out.println(board);

        assertTrue(board.moveLeft());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE0, State.MERGE2,         null,         null },
                { State.MERGE1, State.MERGE1,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1,         null,         null,         null },
        }, board.getBoardState(), "test moveLeft 1");

        assertTrue(board.moveLeft());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE0, State.MERGE2,         null,         null },
                { State.MERGE2,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1,         null,         null,         null },
        }, board.getBoardState(), "test moveLeft 2");

        assertFalse(board.moveLeft());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE0, State.MERGE2,         null,         null },
                { State.MERGE2,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1,         null,         null,         null },
        }, board.getBoardState(), "test moveLeft doesn't change");
    }

    @Test
    public void testMoveRight() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });
        System.out.println(board);

        assertTrue(board.moveRight());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null, State.MERGE0, State.MERGE2 },
                {         null,         null, State.MERGE1, State.MERGE1 },
                {         null,         null,         null, State.MERGE0 },
                {         null,         null,         null, State.MERGE1 },
        }, board.getBoardState(), "test moveRight 1");

        assertTrue(board.moveRight());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null, State.MERGE0, State.MERGE2 },
                {         null,         null,         null, State.MERGE2 },
                {         null,         null,         null, State.MERGE0 },
                {         null,         null,         null, State.MERGE1 },
        }, board.getBoardState(), "test moveRight 2");

        assertFalse(board.moveRight());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null, State.MERGE0, State.MERGE2 },
                {         null,         null,         null, State.MERGE2 },
                {         null,         null,         null, State.MERGE0 },
                {         null,         null,         null, State.MERGE1 },
        }, board.getBoardState(), "test moveRight doesn't change");
    }

    @Test
    public void testScore() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        }, 0);
        System.out.println(board);

        assertTrue(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                // merged 4,   0,               4,             0               = 8
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE1,         null },
                {         null,         null, State.MERGE1,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp 1");
        assertEquals(8, board.getScore(), "test score merged four 2s");

        assertTrue(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                // merged 0      0                8                0           = 8
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE2,         null },
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp 2");
        assertEquals(8 + 8, board.getScore(), "test score merged two 4s");

        assertTrue(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                //  0               0             16             0             = 16
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
                { State.MERGE0,         null,         null,         null },
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp 3");
        assertEquals(8 + 8 + 16, board.getScore(), "test score merged two 8s");

        assertFalse(board.moveUp());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
                { State.MERGE0,         null,         null,         null },
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
        }, board.getBoardState(), "test moveUp doesn't change");
        assertEquals(8 + 8 + 16, board.getScore(), "test score merged nothing");
    }

    @Test
    public void testBoardHistory() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });

        assertTrue(board.moveDown()); // move down
        assertTrue(board.moveDown()); // move down
        assertTrue(board.moveDown()); // move down
        assertFalse(board.moveDown()); // can't move down anymore
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
        }, board.getBoardState(), "board state after merging");
        System.out.println("Board history len: " +  board.getBoardHistoryLength());
        assertEquals(3, board.getBoardHistoryLength(), "Board history length=3");

        /// verify board history
        board.printBoardHistory();
        Stack<Board.BoardState> boardHistory = board.getBoardHistory();
        assertArrayEquals(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        }, boardHistory.get(0).asArray(), "compare boardHistory[0]");
        assertEquals(0, boardHistory.get(0).getScore(), "score boardHistory[0]");

        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE1,         null },
                { State.MERGE1, State.MERGE0, State.MERGE1,         null },
        }, boardHistory.get(1).asArray(), "compare boardHistory[1]");
        assertEquals(8, boardHistory.get(1).getScore(), "score boardHistory[1]");

        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
        }, boardHistory.get(2).asArray(), "compare boardHistory[2]");
        assertEquals(16, boardHistory.get(2).getScore(), "score boardHistory[2]");

    }

    @Test
    public void testUndo() {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });

        assertTrue(board.moveDown());
        assertTrue(board.moveDown());
        assertTrue(board.moveDown());
        assertFalse(board.moveDown());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1, State.MERGE0, State.MERGE3,         null },
        }, board.getBoardState(), "test undo state before undoing");
        assertEquals(8 + 8 + 16, board.getScore(), "test score merged nothing");

        board.printBoardHistory();

        assertTrue(board.undoMove());
        assertEquals(2, board.getBoardHistoryLength(), "Board history length=2");
        System.out.println("Board history len: " +  board.getBoardHistoryLength());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null,         null,         null },
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE1, State.MERGE0, State.MERGE2,         null },
        }, board.getBoardState(), "test undo 1");
        assertEquals(8 + 8, board.getScore(), "test score merged two 4s");

        assertTrue(board.undoMove());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                {         null,         null,         null,         null },
                {         null,         null, State.MERGE2,         null },
                { State.MERGE0,         null, State.MERGE1,         null },
                { State.MERGE1, State.MERGE0, State.MERGE1,         null },
        }, board.getBoardState(), "test undo 2");
        assertEquals(8, board.getScore(), "test score merged four 2s");

        assertTrue(board.undoMove());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        }, board.getBoardState(), "test undo 3");
        assertEquals(0, board.getScore(), "test score zero");

        assertFalse(board.undoMove());
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        }, board.getBoardState(), "test undo didn't change board");
        assertEquals(0, board.getScore(), "test score zero 2");
    }

    @Test
    public void testWinFullBoard() {
        Board board = new Board(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1 },
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE10 },
        });
        System.out.println(board);

        assertFalse(board.moveUp(), "cannot move up");
        assertFalse(board.moveDown(), "cannot move down");
        assertFalse(board.moveLeft(), "cannot move left");
        assertFalse(board.moveRight(), "cannot move right");

        assertEquals(
                Board.GameStatus.WON,
                board.getGameStatus(),
                "game won with full unmoveable board"
        );

    }

    @Test
    public void testLost() {
        Board board = new Board(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1 },
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1 },
        });
        System.out.println(board);

        assertFalse(board.moveUp(), "cannot move up");
        assertFalse(board.moveDown(), "cannot move down");
        assertFalse(board.moveLeft(), "cannot move left");
        assertFalse(board.moveRight(), "cannot move right");

        assertEquals(
                Board.GameStatus.LOST,
                board.getGameStatus(),
                "game won with full unmoveable board"
        );

    }

    @Test
    public void testMoveIntoWin() {
        Board board = new Board(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1 },
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE5 },
                { State.MERGE0, State.MERGE1, State.MERGE9, State.MERGE9 },
        });
        System.out.println(board);

        assertFalse(board.moveUp(), "cannot move up");
        assertFalse(board.moveDown(), "cannot move down");
        assertEquals(
                Board.GameStatus.PLAYING,
                board.getGameStatus(),
                "game status initially playing"
        );

        assertTrue(board.moveLeft(), "merged left");
        board.generateNewTile(); // has to generate in bottom right corner, unable to merge with
                                 // any other tile
        assertTrue(Arrays.deepEquals(board.getBoardState(), new State[][]{
                {State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0},
                {State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1},
                {State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE5},
                {State.MERGE0, State.MERGE1, State.MERGE10, State.MERGE0},
        }) || Arrays.deepEquals(board.getBoardState(), new State[][]{
                {State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0},
                {State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1},
                {State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE5},
                {State.MERGE0, State.MERGE1, State.MERGE10, State.MERGE1},
        }), "validate board state after merge");
        System.out.println(board);
        assertFalse(board.moveUp(), "cannot move up after merge");
        assertFalse(board.moveDown(), "cannot move down after merge");
        assertFalse(board.moveLeft(), "cannot move left after merge");
        assertFalse(board.moveRight(), "cannot move right after merge");
        assertEquals(
                Board.GameStatus.WON,
                board.getGameStatus(),
                "game won by merging into full unmoveable board"
        );

    }

    @Test
    public void testMoveIntoLoss() {
        Board board = new Board(new State[][]{
                { State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1 },
                { State.MERGE4, State.MERGE0, State.MERGE1, State.MERGE0 },
                { State.MERGE5, State.MERGE2, State.MERGE7, State.MERGE7 },
        });
        System.out.println(board);

        assertFalse(board.moveUp(), "cannot move up");
        assertFalse(board.moveDown(), "cannot move down");
        assertEquals(
                Board.GameStatus.PLAYING,
                board.getGameStatus(),
                "game status initially playing"
        );

        assertTrue(board.moveRight(), "merged right");
        board.generateNewTile(); // has to generate in bottom left corner, unable to merge with
                                 // any other tile
        assertTrue(Arrays.deepEquals(board.getBoardState(), new State[][]{
                {State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0},
                {State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1},
                {State.MERGE4, State.MERGE0, State.MERGE1, State.MERGE0},
                {State.MERGE0, State.MERGE5, State.MERGE2, State.MERGE8},
        }) || Arrays.deepEquals(board.getBoardState(), new State[][]{
                {State.MERGE1, State.MERGE0, State.MERGE1, State.MERGE0},
                {State.MERGE0, State.MERGE1, State.MERGE0, State.MERGE1},
                {State.MERGE4, State.MERGE0, State.MERGE1, State.MERGE0},
                {State.MERGE1, State.MERGE5, State.MERGE2, State.MERGE8},
        }), "validate board state after merge");
        System.out.println(board);
        assertFalse(board.moveUp(), "cannot move up after merge");
        assertFalse(board.moveDown(), "cannot move down after merge");
        assertFalse(board.moveLeft(), "cannot move left after merge");
        assertFalse(board.moveRight(), "cannot move right after merge");
        assertEquals(
                Board.GameStatus.LOST,
                board.getGameStatus(),
                "game lost by merging into full unmoveable board"
        );

    }

    @Test
    public void testSaveGame() throws IOException {
        Board board = new Board(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        });
        System.out.println(board);

        // move left 2 times
        assertTrue(board.moveLeft());
        assertTrue(board.moveLeft());

        // write to file
        String filePath = "src/test/java/org/cis1200/game2048/game_state_test.txt";
        GameSaverLoader gameSaverLoader = new GameSaverLoader(filePath);
        board.saveGame(gameSaverLoader);


        // validate file output
        String expected =
                "16 4\n" +
                "0 2 -1 -1 \n" +
                "2 -1 -1 -1 \n" +
                "0 -1 -1 -1 \n" +
                "1 -1 -1 -1 \n" +
                "2\n" +
                "0 4\n" +
                "0 -1 2 -1 \n" +
                "0 0 1 -1 \n" +
                "-1 -1 0 -1 \n" +
                "0 -1 0 -1 \n" +
                "8 4\n" +
                "0 2 -1 -1 \n" +
                "1 1 -1 -1 \n" +
                "0 -1 -1 -1 \n" +
                "1 -1 -1 -1 \n";
        int numLines = 16;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numLines; i++) {
            sb.append(reader.readLine()).append("\n");
        }
        System.out.println("Actual file contents:");
        System.out.println(sb);
        assertEquals(expected, sb.toString(), "check file output");

    }

    @Test
    public void testLoadGame() throws IOException {
        // write dummy data to file
        String filePath = "src/test/java/org/cis1200/game2048/game_state_test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        String data =
                "16 4\n" +
                "0 2 -1 -1 \n" +
                "2 -1 -1 -1 \n" +
                "0 -1 -1 -1 \n" +
                "1 -1 -1 -1 \n" +
                "2\n" +
                "0 4\n" +
                "0 -1 2 -1 \n" +
                "0 0 1 -1 \n" +
                "-1 -1 0 -1 \n" +
                "0 -1 0 -1 \n" +
                "8 4\n" +
                "0 2 -1 -1 \n" +
                "1 1 -1 -1 \n" +
                "0 -1 -1 -1 \n" +
                "1 -1 -1 -1 \n";
        writer.write(data);
        writer.flush();
        writer.close();

        // load game
        GameSaverLoader gameSaverLoader = new GameSaverLoader(filePath);
        Board board = gameSaverLoader.loadGame();

        /// verify states & history
        // current
        System.out.println(board);
        assertArrayEquals(new State[][]{
                { State.MERGE0, State.MERGE2,         null,         null },
                { State.MERGE2,         null,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1,         null,         null,         null },
        }, board.getBoardState(), "check final board state");
        assertEquals(16, board.getScore(), "check final score");

        // history
        board.printBoardHistory();
        Stack<Board.BoardState> boardHistory = board.getBoardHistory();
        assertEquals(2, boardHistory.size(), "check history size");
        assertArrayEquals(new State[][]{
                { State.MERGE0,         null, State.MERGE2,         null },
                { State.MERGE0, State.MERGE0, State.MERGE1,         null },
                {         null,         null, State.MERGE0,         null },
                { State.MERGE0,         null, State.MERGE0,         null },
        }, boardHistory.get(0).asArray(), "verify boardHistory[0]");
        assertEquals(0, boardHistory.get(0).getScore(), "score boardHistory[0]");

        assertArrayEquals(new State[][]{
                { State.MERGE0, State.MERGE2,         null,         null },
                { State.MERGE1, State.MERGE1,         null,         null },
                { State.MERGE0,         null,         null,         null },
                { State.MERGE1,         null,         null,         null },
        }, boardHistory.get(1).asArray(), "verify boardHistory[1]");
        assertEquals(8, boardHistory.get(1).getScore(), "score boardHistory[1]");

    }

}
