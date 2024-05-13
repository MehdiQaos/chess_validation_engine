package dev.mehdi;

import java.util.Objects;

public class Position {
    private static Position[][] positions = new Position[8][8];
    int row;
    int col;

    static {
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j)
                positions[i][j] = new Position(i + 1, j + 1);
    }

    public static Position of(int row, int col) {
        return positions[row - 1][col - 1];
    }

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}