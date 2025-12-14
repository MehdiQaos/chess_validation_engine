package dev.mehdi.piece;

import dev.mehdi.Color;
import dev.mehdi.Position;
import dev.mehdi.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Piece {
    private Color color;
    private Type type;
    private Position position;

    public Piece() {}

    public Piece(Color color, Type type, Position position) {
        this.color = color;
        this.type = type;
        this.position = position;
    }

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public boolean isWhite() {
        return color == Color.WHITE;
    }

    public boolean isBlack() {
        return color == Color.BLACK;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", type=" + type +
                ", position=" + position +
                '}';
    }

    public static Piece pawn(Color color, int row, int col) {
        return new Piece(Color.WHITE, Type.PAWN, Position.of(row, col));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return color == piece.color && type == piece.type && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type, position);
    }
}
