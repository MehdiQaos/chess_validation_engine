package dev.mehdi;

import dev.mehdi.piece.Piece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ChessTest {

    private GameState state;
    private Player whitePlayer;
    private Player blackPlayer;
    private Board board;
    private Chess chess;

    @BeforeEach
    void setUp() {
        state = GameState.PLAYING;
        whitePlayer = new Player(Color.WHITE);
        blackPlayer = new Player(Color.BLACK);
        board = new Board();
        chess = new Chess(board, whitePlayer, blackPlayer, blackPlayer, state);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_pawn_on_d4_moves() throws Exception {
        Method method = chess.getClass().getDeclaredMethod("pawnMoves", Piece.class);
        method.setAccessible(true);
        Piece d2 = board.get(Position.of(2, 4));

        Set<PieceMove> expected = Set.of(
                new PieceMove(d2, Position.of(3, 4)),
                new PieceMove(d2, Position.of(4, 4))
        );
        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_pawn_on_d3_moves() throws Exception {
        board.move(Position.of(2, 4), Position.of(3, 4));
        Method method = chess.getClass().getDeclaredMethod("pawnMoves", Piece.class);
        method.setAccessible(true);
        Piece d3 = board.get(Position.of(3, 4));
        Set<PieceMove> expected = Set.of(
                new PieceMove(d3, Position.of(4, 4))
        );

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d3);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_black_pawn_on_d7_moves() throws Exception {
        Method method = chess.getClass().getDeclaredMethod("pawnMoves", Piece.class);
        method.setAccessible(true);
        Piece d7 = board.get(Position.of(7, 4));
        Set<PieceMove> expected = Set.of(
                new PieceMove(d7, Position.of(6, 4)),
                new PieceMove(d7, Position.of(5, 4))
        );

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d7);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_white_rook_on_d4_moves() throws Exception {
        board.move(Position.of(1, 1), Position.of(4, 4));
        Method method = chess.getClass().getDeclaredMethod("rookMoves", Piece.class);
        method.setAccessible(true);
        Piece d4 = board.get(Position.of(4, 4));
        Set<PieceMove> expected = Set.of(
                new PieceMove(d4, Position.of(3, 4)),
                new PieceMove(d4, Position.of(5, 4)),
                new PieceMove(d4, Position.of(6, 4)),
                new PieceCapture(d4, Position.of(7, 4), board.get(Position.of(7, 4))),
                new PieceMove(d4, Position.of(4, 3)),
                new PieceMove(d4, Position.of(4, 2)),
                new PieceMove(d4, Position.of(4, 1)),
                new PieceMove(d4, Position.of(4, 5)),
                new PieceMove(d4, Position.of(4, 6)),
                new PieceMove(d4, Position.of(4, 7)),
                new PieceMove(d4, Position.of(4, 8))
        );

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d4);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_white_rook_on_d1_moves() throws Exception {
        Method method = chess.getClass().getDeclaredMethod("rookMoves", Piece.class);
        method.setAccessible(true);
        Piece d1 = board.get(Position.of(1, 1));
        Set<PieceMove> expected = Collections.emptySet();

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_black_rook_on_a6_moves() throws Exception {
        board.move(Position.of(8, 1), Position.of(6, 1));
        Method method = chess.getClass().getDeclaredMethod("rookMoves", Piece.class);
        method.setAccessible(true);
        Piece a6 = board.get(Position.of(6, 1));
        Set<PieceMove> expected = Set.of(
                new PieceMove(a6, Position.of(6, 2)),
                new PieceMove(a6, Position.of(6, 3)),
                new PieceMove(a6, Position.of(6, 4)),
                new PieceMove(a6, Position.of(6, 5)),
                new PieceMove(a6, Position.of(6, 6)),
                new PieceMove(a6, Position.of(6, 7)),
                new PieceMove(a6, Position.of(6, 8)),
                new PieceMove(a6, Position.of(5, 1)),
                new PieceMove(a6, Position.of(4, 1)),
                new PieceMove(a6, Position.of(3, 1)),
                new PieceCapture(a6, Position.of(2, 1), board.get(Position.of(2, 1)))
        );

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, a6);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_black_bishop_on_c1_moves() throws Exception {
        Method method = chess.getClass().getDeclaredMethod("rookMoves", Piece.class);
        method.setAccessible(true);
        Piece c1 = board.get(Position.of(1, 3));
        Set<PieceMove> expected = Collections.emptySet();

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, c1);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_black_bishop_on_d5_moves() throws Exception {
        Method method = chess.getClass().getDeclaredMethod("bishopMoves", Piece.class);
        method.setAccessible(true);
        board.move(Position.of(8, 3), Position.of(5, 4));
        Piece p = board.get(Position.of(5, 4));
        Set<PieceMove> expected = Set.of(
                new PieceMove(p, Position.of(6, 5)),
                new PieceMove(p, Position.of(6, 3)),
                new PieceMove(p, Position.of(4, 5)),
                new PieceMove(p, Position.of(3, 6)),
                new PieceCapture(p, Position.of(2, 7), board.get(Position.of(2, 7))),
                new PieceMove(p, Position.of(4, 3)),
                new PieceMove(p, Position.of(3, 2)),
                new PieceCapture(p, Position.of(2, 1), board.get(Position.of(2, 1)))
        );

        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, p);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_white_queen_on_d1_moves() throws Exception {
        Piece queen = board.get(Position.of(1, 4));
        Set<PieceMove> expected = Set.of();

        Set<PieceMove> result = chess.queenMoves(queen);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void test_white_king_on_d4_moves() throws Exception {
        board.move(Position.of(1, 5), Position.of(4, 4));
        Piece king = board.get(Position.of(4, 4));
        Set<PieceMove> expected = Set.of(
                new PieceMove(king, Position.of(5, 3)),
                 new PieceMove(king, Position.of(5, 4)),
                new PieceMove(king, Position.of(5, 5)),
                new PieceMove(king, Position.of(4, 5)),
                new PieceMove(king, Position.of(4, 3)),
                new PieceMove(king, Position.of(3, 3)),
                new PieceMove(king, Position.of(3, 4)),
                new PieceMove(king, Position.of(3, 5))
        );

        Set<PieceMove> result = chess.kingMoves(king);
        assertThat(result).isEqualTo(expected);
    }

}