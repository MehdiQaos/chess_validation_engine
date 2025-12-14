package dev.mehdi.Helper;

import dev.mehdi.piece.Piece;

public class PrintBoard {
    public static String PieceToUnicode(Piece piece) {
        return switch (piece.getType()) {
            case PAWN -> piece.isWhite() ? "♟" : "♙";
            case ROOK -> piece.isWhite() ? "♜" : "♖";
            case KNIGHT -> piece.isWhite() ? "♞" : "♘";
            case BISHOP -> piece.isWhite() ? "♝" : "♗";
            case QUEEN -> piece.isWhite() ? "♛" : "♕";
            case KING -> piece.isWhite() ? "♚" : "♔";
        };
    }

    public static String PieceToAscii(Piece piece) {
        return switch (piece.getType()) {
            case PAWN -> piece.isWhite() ? "P" : "p";
            case ROOK -> piece.isWhite() ? "R" : "r";
            case KNIGHT -> piece.isWhite() ? "N" : "n";
            case BISHOP -> piece.isWhite() ? "B" : "b";
            case QUEEN -> piece.isWhite() ? "Q" : "q";
            case KING -> piece.isWhite() ? "K" : "k";
        };
    }
}
