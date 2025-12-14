package dev.mehdi;

import dev.mehdi.Helper.PrintBoard;
import dev.mehdi.piece.Piece;
import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private static final Map<Character, Type> CHAR_TO_TYPE_MAP = Map.ofEntries(
            Map.entry('p', Type.PAWN),
            Map.entry('P', Type.PAWN),
            Map.entry('r', Type.ROOK),
            Map.entry('R', Type.ROOK),
            Map.entry('n', Type.KNIGHT),
            Map.entry('N', Type.KNIGHT),
            Map.entry('b', Type.BISHOP),
            Map.entry('B', Type.BISHOP),
            Map.entry('q', Type.QUEEN),
            Map.entry('Q', Type.QUEEN),
            Map.entry('k', Type.KING),
            Map.entry('K', Type.KING)
    );

    private Piece[][] board = new Piece[8][8];
    @Getter
    private Set<Piece> whitePieces = new HashSet<>();
    @Getter
    private Set<Piece> blackPieces = new HashSet<>();

    public Board(Piece[][] pieces) {
        this.board = pieces;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) continue;
                Piece piece = board[i][j];
                piece.setPosition(Position.of(i + 1, j + 1));
                if (piece.isWhite())
                    whitePieces.add(piece);
                else if(piece.isBlack())
                    blackPieces.add(piece);
            }
        }
    }

    public Board() {
//        TODO: make it a factory method
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

    public static Board fromFEN(String input) {
        String fen = input.split("\\s")[0];
        if (!fen.matches("[prnbqkPRNBQK1-8/]+")) {
            throw new IllegalArgumentException("Invalid FEN string: " + fen);
        }
        String[] fenRows = fen.split("/");
        if (fenRows.length != 8) {
            throw new IllegalArgumentException("Invalid FEN string: " + fen);
        }

        Piece[][] board = new Piece[8][8];
        int i = 7;
        for (String row : fenRows) {
            int j = 0;
            for (char c : row.toCharArray()) {
                char lc = Character.toLowerCase(c);
                if (Character.isDigit(lc)) {
                    j += Character.getNumericValue(lc);
                } else if (lc == 'p' || lc == 'r' || lc == 'n' || lc == 'b' || lc == 'q' || lc == 'k') {
                    Type type = CHAR_TO_TYPE_MAP.get(c);
                    Color color = Character.isLowerCase(c) ? Color.BLACK : Color.WHITE;
                    Piece piece = new Piece(color, type, Position.of(i + 1, j + 1));
                    board[i][j] = piece;
                    piece.setPosition(Position.of(i + 1, j + 1));
                    j++;
                } else {
                    throw new IllegalArgumentException("Invalid FEN string: " + fen);
                }
            }
            i--;
        }
        return new Board(board);
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

    void move(Position fromPosition, Position toPosition) {
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

    void set(Piece piece, Position pos) {
        validatePosition(pos);
        board[pos.row - 1][pos.col - 1] = piece;
        piece.setPosition(pos);
    }

    void remove(Position pos) {
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

    void validatePosition(Position pos) {
        if (pos.row < 1 || pos.row > 8 || pos.col < 1 || pos.col > 8)
            throw new RuntimeException("Invalid position, coordinates out of range: " + pos);
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece == null) {
                    sb.append('.');
                } else {
                    String s = PrintBoard.PieceToAscii(piece);
                    sb.append(s);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}