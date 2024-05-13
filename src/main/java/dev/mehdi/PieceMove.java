package dev.mehdi;

import dev.mehdi.piece.Piece;

import java.util.Objects;

public class PieceMove {
    Position target;
    Piece piece;

    public PieceMove() {
    }

    PieceMove(Piece piece, Position target) {
        this.piece = piece;
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PieceMove pieceMove)) return false;
        return  Objects.equals(target, pieceMove.target) &&
                Objects.equals(piece, pieceMove.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, piece);
    }
}
