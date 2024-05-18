package dev.mehdi;

import dev.mehdi.piece.Piece;

import java.util.Objects;

public class PieceCapture extends PieceMove {
    Piece capturedPiece;

    public PieceCapture(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public PieceCapture(Piece piece, Position destination, Piece capturedPiece) {
        super(piece, destination);
        this.capturedPiece = capturedPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PieceCapture that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(capturedPiece, that.capturedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), capturedPiece);
    }

    @Override
    public String toString() {
        return "C{" +
                capturedPiece +
                ", " + target +
                ", " + piece +
                '}';
    }
}
