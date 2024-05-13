package dev.mehdi;

import dev.mehdi.piece.Piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chess {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player turn;
    private GameState state;

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
        if (row == 2 || row == 7) {
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
            if (nextPiece == null || nextPiece.getColor() != pawn.getColor()) {
                PieceMove move = new PieceCapture(pawn, pos, nextPiece);
                availableMoves.add(move);
            }
        }
        if (col > 1) {
            pos = Position.of(row + direction, col - 1);
            Piece nextPiece = board.get(pos);
            if (nextPiece == null || nextPiece.getColor() != pawn.getColor()) {
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
        int row = rook.getPosition().row;
        int col = rook.getPosition().col;
        Color color = rook.getColor();
        Set<PieceMove> availableMoves = new HashSet<>();
        List<Dir> dirs = List.of(
                new Dir(1, "ROW"),
                new Dir(-1, "ROW"),
                new Dir(1, "COL"),
                new Dir(-1, "COL")
        );
        for (Dir d: dirs) {
            for (int i = (d.type.equals("ROW") ? row: col) + d.di; 1 <= i && i <= 8; i += d.di) {
                int r = d.type.equals("ROW") ? i: row;
                int c = d.type.equals("ROW") ? col: i;
                Position pos = Position.of(r, c);
                Piece piece = board.get(pos);
                if (piece == null) {
                    PieceMove pieceMove = new PieceMove(rook, pos);
                    availableMoves.add(pieceMove);
                } else if (piece.getColor() != color) {
                    PieceMove pieceMove = new PieceCapture(rook, pos, piece);
                    availableMoves.add(pieceMove);
                    break;
                } else {
                    break;
                }
            }
        }
        return availableMoves;
    }

    record Dir(int di, String type){}
}
