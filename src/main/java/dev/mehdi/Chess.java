package dev.mehdi;

import dev.mehdi.piece.Piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Chess {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player turn;
    private GameState state;
    public static final int BOARD_DIM_LENGTH = 8;

    private final List<Dir> STRAIGHT_DIRS = List.of(
            new Dir(1, 0),
            new Dir(-1, 0),
            new Dir(0, 1),
            new Dir(0, -1)
    );

    private final List<Dir> DIAGONAL_DIRS = List.of(
            new Dir(1, 1),
            new Dir(-1, 1),
            new Dir(-1, -1),
            new Dir(1, -1)
    );

    public Chess() {
        whitePlayer = new Player(Color.WHITE);
        blackPlayer = new Player(Color.BLACK);
        board = new Board();
        state = GameState.NOT_STARTED;
    }

    public Chess(Board board, Player whitePlayer, Player blackPlayer, Player turn,
                 GameState state) {
        this.board = board;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.turn = turn;
        this.state = state;
    }

    public boolean isPlaying() {
        return state.equals(GameState.PLAYING);
    }

    public void start() {
    }

    private Player otherPlayer() {
        if (!isPlaying()) {
            return null;
        }
        return whitePlayer == turn ? blackPlayer : whitePlayer;
    }

    public Response move(Position from, Position to) {
        if (!isPlaying()) {
            return Response.invalid();
        }

        Player player = turn;
        Player opponent = otherPlayer();
        board.move(from, to);
        return Response.invalid();
    }

    private Boolean isKingInCheck(Color color) {
        Piece king = color == whitePlayer.color ? whitePlayer.king : blackPlayer.king;
        return false;
    }

    private Set<PieceMove> pawnMoves(Piece pawn) {
        int direction = pawn.getColor() == Color.WHITE ? 1 : -1;
        int row = pawn.getPosition().row;
        int col = pawn.getPosition().col;
        Piece enPassant = null;
        Set<PieceMove> availableMoves = new HashSet<>();
        if (row >= 8 || row <= 0) {
            throw new IllegalStateException("illegal pawn position");
        }
        Position pos = Position.of(row + direction, col);
        if (board.get(pos) == null) {
            PieceMove move = new PieceMove(pawn, pos);
            availableMoves.add(move);
        }
        if (row == 2 && pawn.isWhite() || row == 7 && pawn.isBlack()) {
            pos = Position.of(row + 2 * direction, col);
            Piece piece = board.get(pos);
            if (piece == null) {
                PieceMove move = new PieceMove(pawn, pos);
                availableMoves.add(move);
            }
        }
        if (col < 8) {
            pos = Position.of(row + direction, col + 1);
            Piece nextPiece = board.get(pos);
            if (nextPiece != null && nextPiece.getColor() != pawn.getColor()) {
                PieceMove move = new PieceCapture(pawn, pos, nextPiece);
                availableMoves.add(move);
            }
        }
        if (col > 1) {
            pos = Position.of(row + direction, col - 1);
            Piece nextPiece = board.get(pos);
            if (nextPiece != null && nextPiece.getColor() != pawn.getColor()) {
                PieceMove move = new PieceCapture(pawn, pos, nextPiece);
                availableMoves.add(move);
            }
        }
        if (enPassant != null) {
            PieceMove move = new PieceCapture(pawn, pos, enPassant);
        }
        return availableMoves;
    }

    private Set<PieceMove> rookMoves(Piece rook) {
        return straightMoves(rook, BOARD_DIM_LENGTH - 1);
    }

    public Set<PieceMove> bishopMoves(Piece bishop) {
        return diagonalMoves(bishop, BOARD_DIM_LENGTH - 1);
    }

    public Set<PieceMove> queenMoves(Piece queen) {
        return straightAndDiagMoves(queen, BOARD_DIM_LENGTH - 1);
    }

    public Set<PieceMove> kingMoves(Piece king) {
        var moves = straightAndDiagMoves(king, 1);
        Piece otherKing = king.isWhite() ? blackPlayer.king : whitePlayer.king;
        Set<Position> otherKingMoves = straightAndDiagMoves(otherKing, 1)
                .stream()
                .map(pm -> pm.target).collect(Collectors.toSet());
        return moves.stream()
                .filter(m -> !otherKingMoves.contains(m.target))
                .collect(Collectors.toSet());
    }

    public Set<PieceMove> straightAndDiagMoves(Piece piece, int dirLimit) {
        var moves = straightMoves(piece, dirLimit);
        moves.addAll(diagonalMoves(piece, dirLimit));
        return moves;
    }

    public Set<PieceMove> straightMoves(Piece piece, int dirLimit) {
        return dirMoves(piece, STRAIGHT_DIRS, dirLimit);
    }

    public Set<PieceMove> diagonalMoves(Piece piece, int dirLimit) {
         return dirMoves(piece, DIAGONAL_DIRS, dirLimit);
    }

    private Set<PieceMove> dirMoves(Piece piece, List<Dir> dirs, int dirLimit) {
        int row = piece.getPosition().row;
        int col = piece.getPosition().col;
        Color color = piece.getColor();
        Set<PieceMove> availableMoves = new HashSet<>();
        for (Dir d: dirs) {
            int r = row + d.di, c = col + d.dj, limit = dirLimit;
            while (limit > 0) {
                if (r < 1 || r > 8 || c < 1 || c > 8) {
                    break;
                }
                Position pos = Position.of(r, c);
                Piece newPiece = board.get(pos);
                if (newPiece == null) {
                    PieceMove pieceMove = new PieceMove(piece, pos);
                    availableMoves.add(pieceMove);
                } else if (newPiece.getColor() != color) {
                    PieceMove pieceMove = new PieceCapture(piece, pos, newPiece);
                    availableMoves.add(pieceMove);
                    break;
                } else {
                    break;
                }
                r += d.di; c += d.dj;
                limit--;
            }
        }
        return availableMoves;
    }

    record Dir(int di, int dj){}
}
