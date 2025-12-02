package org.cis1200.game2048;

import org.cis1200.game2048.Tile.State;
import org.junit.jupiter.api.*;
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
        assertEquals(3, board.getBoardHistoryLength(), "Board history length=3");
        System.out.println("Board history len: " +  board.getBoardHistoryLength());
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

    }

}
