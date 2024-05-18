package dev.mehdi;

import dev.mehdi.piece.Piece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ChessTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_pawn_on_d4_moves() throws Exception {
//        initialization
        GameState state = GameState.PLAYING;
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Board board = new Board();
        Chess chess = new Chess(board, whitePlayer, blackPlayer, whitePlayer, state);
        Method method = chess.getClass().getDeclaredMethod("pawnMoves", Piece.class);
        method.setAccessible(true);
        Piece d2 = board.get(Position.of(2, 4));


        Set<PieceMove> expected = Set.of(
                new PieceMove(d2, Position.of(3, 4)),
                new PieceMove(d2, Position.of(4, 4)),
                new PieceMove(d2, Position.of(3, 3)),
                new PieceMove(d2, Position.of(3, 5))
        );
        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d2);
        System.out.println(result.size());
        assertThat(result.size()).isEqualTo(4);
        assertThat(result).containsAll(expected);
    }

    @Test
    void test_pawn_on_d3_moves() throws Exception {
//        initialization
        GameState state = GameState.PLAYING;
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Board board = new Board();
        board.move(Position.of(2, 4), Position.of(3, 4));
        Chess chess = new Chess(board, whitePlayer, blackPlayer, whitePlayer, state);
        Method method = chess.getClass().getDeclaredMethod("pawnMoves", Piece.class);
        method.setAccessible(true);
        Piece d3 = board.get(Position.of(3, 4));


        Set<PieceMove> expected = Set.of(
                new PieceMove(d3, Position.of(4, 4)),
                new PieceMove(d3, Position.of(4, 3)),
                new PieceMove(d3, Position.of(4, 5))
        );
        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d3);
        assertThat(result.size()).isEqualTo(3);
        assertThat(result).containsAll(expected);
    }

    @Test
    void test_black_pawn_on_d7_moves() throws Exception {
//        initialization
        GameState state = GameState.PLAYING;
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Board board = new Board();
        Chess chess = new Chess(board, whitePlayer, blackPlayer, blackPlayer, state);
        Method method = chess.getClass().getDeclaredMethod("pawnMoves", Piece.class);
        method.setAccessible(true);
        Piece d7 = board.get(Position.of(7, 4));


        Set<PieceMove> expected = Set.of(
                new PieceMove(d7, Position.of(6, 4)),
                new PieceMove(d7, Position.of(5, 4)),
                new PieceMove(d7, Position.of(6, 3)),
                new PieceMove(d7, Position.of(6, 5))
        );
        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d7);
        assertThat(result.size()).isEqualTo(4);
        assertThat(result).containsAll(expected);
    }

    @Test
    void test_white_rook_on_d1_moves() throws Exception {
//        initialization
        GameState state = GameState.PLAYING;
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Board board = new Board();
        Chess chess = new Chess(board, whitePlayer, blackPlayer, blackPlayer, state);
        board.move(Position.of(1, 1), Position.of(4, 4));
        Method method = chess.getClass().getDeclaredMethod("rookMoves", Piece.class);
        method.setAccessible(true);
        Piece d1 = board.get(Position.of(4, 4));


        Set<PieceMove> expected = Set.of(
                new PieceMove(d1, Position.of(3, 4)),
                new PieceMove(d1, Position.of(5, 4)),
                new PieceMove(d1, Position.of(6, 4)),
                new PieceCapture(d1, Position.of(7, 4), board.get(Position.of(7, 4))),
                new PieceMove(d1, Position.of(4, 3)),
                new PieceMove(d1, Position.of(4, 2)),
                new PieceMove(d1, Position.of(4, 1)),
                new PieceMove(d1, Position.of(4, 5)),
                new PieceMove(d1, Position.of(4, 6)),
                new PieceMove(d1, Position.of(4, 7)),
                new PieceMove(d1, Position.of(4, 8))
        );
        Set<PieceMove> result = (Set<PieceMove>) method.invoke(chess, d1);
        assertThat(result.size()).isEqualTo(11);
        assertThat(result).containsAll(expected);
    }
}