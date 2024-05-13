package dev.mehdi;

import dev.mehdi.piece.Piece;

public class PieceCapture extends PieceMove {
    Piece capturedPiece;

    public PieceCapture(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public PieceCapture(Piece piece, Position destination, Piece capturedPiece) {
        super(piece, destination);
        this.capturedPiece = capturedPiece;
    }
}
