package org.cis1200.game2048;

import java.awt.*;
import java.util.Objects;

public class Tile {

    /**
     * Set of all possible tile states, including each tile's point value.
     */
    public enum State {
        MERGE0(2, "#eee6da", "#757166"), // 2
        MERGE1(4, "#ece0c8", "#757166"), // 4
        MERGE2(8, "#efb27c", "#f4f9f7"), // 8
        MERGE3(16, "#f29768", "#f4f9f7"), // 16
        MERGE4(32, "#ef7c62", "#f4f9f7"), // 32
        MERGE5(64, "#ed5f40", "#f4f9f7"), // 64
        MERGE6(128, "#ebcf77", "#f4f9f7"), // 128
        MERGE7(256, "#edcb67", "#f4f9f7"), // 256
        MERGE8(512, "#ecc859", "#f4f9f7"), // 512
        MERGE9(1024, "#e6c257", "#f4f9f7"), // 1024
        MERGE10(2048, "#e8be4f", "#f4f9f7"); // 2048

        private final int points;
        private final Color bgColor, textColor;

        /**
         * @return This tile's score value.
         */
        public int getPoints() {
            return this.points;
        }

        /**
         * @return This tile's background color.
         */
        public Color getBgColor() {
            return this.bgColor;
        }

        /**
         * @return This tile's text color.
         */
        public Color getTextColor() {
            return this.textColor;
        }

        /**
         * Gets the next tile state after the given one. Throws {@code IllegalArgumentException} if
         * this tile is {@code State.MERGE10} or invalid.
         * @return The next state.
         */
        public State nextState() {
            if (this == State.MERGE10) {
                throw new IllegalArgumentException("State cannot be MERGE10.");
            }
            return switch (this) {
                case MERGE0 -> MERGE1;
                case MERGE1 -> MERGE2;
                case MERGE2 -> MERGE3;
                case MERGE3 -> MERGE4;
                case MERGE4 -> MERGE5;
                case MERGE5 -> MERGE6;
                case MERGE6 -> MERGE7;
                case MERGE7 -> MERGE8;
                case MERGE8 -> MERGE9;
                case MERGE9 -> MERGE10;
                default -> throw new IllegalArgumentException("Unknown state.");
            };
        }

        State(int points, String bgColor, String textColor) {
            this.points = points;
            this.bgColor = Color.decode(bgColor);
            this.textColor = Color.decode(textColor);
        }
    }

    private State state;

    /**
     * Creates a new {@code Tile} with the given initial state.
     * @param state An initial state.
     */
    public Tile(State state) {
        this.state = state;
    }

    /**
     * @return This {@code Tile}'s current state.
     */
    public State getState() {
        return this.state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tile tile = (Tile) o;
        return state == tile.state;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }

    /**
     * Change the current state to next. Preserves invariant that the tile can never go backwards
     * in value.
     */
    public void upgrade() {
        this.state = state.nextState();
    }


}
