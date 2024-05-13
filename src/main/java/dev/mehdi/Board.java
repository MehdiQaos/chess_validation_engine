package dev.mehdi;

import dev.mehdi.piece.Piece;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private Piece[][] board = new Piece[8][8];
    @Getter
    private Set<Piece> whitePieces = new HashSet<>();
    @Getter
    private Set<Piece> blackPieces = new HashSet<>();

    public Board() {
        for (int i = 1; i <= 8; i++) {
            newPiece(Type.PAWN, Color.WHITE, Position.of(2, i));
            newPiece(Type.PAWN, Color.BLACK, Position.of(7, i));
        }

        newPiece(Type.ROOK, Color.WHITE, Position.of(1, 1));
        newPiece(Type.ROOK, Color.WHITE, Position.of(1, 8));
        newPiece(Type.ROOK, Color.BLACK, Position.of(8, 1));
        newPiece(Type.ROOK, Color.BLACK, Position.of(8, 8));

        newPiece(Type.KNIGHT, Color.WHITE, Position.of(1, 2));
        newPiece(Type.KNIGHT, Color.WHITE, Position.of(1, 7));
        newPiece(Type.KNIGHT, Color.BLACK, Position.of(8, 2));
        newPiece(Type.KNIGHT, Color.BLACK, Position.of(8, 7));

        newPiece(Type.BISHOP, Color.WHITE, Position.of(1, 3));
        newPiece(Type.BISHOP, Color.WHITE, Position.of(1, 6));
        newPiece(Type.BISHOP, Color.BLACK, Position.of(8, 3));
        newPiece(Type.BISHOP, Color.BLACK, Position.of(8, 6));

        newPiece(Type.QUEEN, Color.WHITE, Position.of(1, 4));
        newPiece(Type.QUEEN, Color.BLACK, Position.of(8, 4));

        newPiece(Type.KING, Color.WHITE, Position.of(1, 5));
        newPiece(Type.KING, Color.BLACK, Position.of(8, 5));
    }

    private void newPiece(Type type, Color color, Position position) {
        Piece piece = new Piece(color, type);
        set(piece, position);
        if (color == Color.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    void move(@NonNull Position fromPosition, @NonNull Position toPosition) {
        Piece pieceFrom = get(fromPosition);
        if (pieceFrom == null) {
            throw new IllegalStateException("move piece cant be null");
        }
        Piece pieceTo = get(toPosition);
        set(pieceFrom, toPosition);
        remove(fromPosition);
    }

    Piece get(Position pos) {
        validatePosition(pos);
        return board[pos.row - 1][pos.col - 1];
    }

    void set(@NonNull Piece piece, @NonNull Position pos) {
        validatePosition(pos);
        board[pos.row - 1][pos.col - 1] = piece;
        piece.setPosition(pos);
    }

    void remove(@NonNull Position pos) {
        validatePosition(pos);
        Piece piece = get(pos);
        if (piece == null) {
            return;
        }
        if (piece.isWhite()) {
            whitePieces.remove(piece);
        }
        if (piece.isBlack()) {
            blackPieces.remove(piece);
        }
        board[pos.row - 1][pos.col - 1] = null;
    }

    void validatePosition(@NonNull Position pos) {
        if (pos.row < 1 || pos.row > 8 || pos.col < 1 || pos.col > 8)
            throw new RuntimeException("Invalid position coordinates out of range: " + pos);
    }

    public Piece getWhiteKing() {
        return whitePieces.stream()
                .filter(p -> p.getType() == Type.KING && p.isWhite())
                .findAny().orElseThrow(
                        () -> new IllegalStateException("White king does not exist")
                );
    }

    public Piece getBlackKing() {
        return whitePieces.stream()
                .filter(p -> p.getType() == Type.KING && p.isWhite())
                .findAny().orElseThrow(
                        () -> new IllegalStateException("Black king does not exist")
                );
    }
}