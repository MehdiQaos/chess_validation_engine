package dev.mehdi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fromFEN() {
        String[] fens = {
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            "rnbqkbnr/pppppppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2",
            "r1bqkbnr/pppppppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 2 3",
            "rnbqkbnr/pppppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2",
            "4k3/5p2/8/8/8/1b6/p1r5/K7 w - - 0 23",
            "rnbqkbnr/ppp1pppp/8/3p4/2P5/8/PP1PPPPP/RNBQKBNR b KQkq - 0 2",
            "rnbqkbnr/pppppppp/4p3/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2",
            "r1bqk1nr/pppppppp/2n5/2b1p3/2B1P3/5N2/PPPP1PPP/RNBQK2R w KQkq - 4 4",
            "rnbqkbnr/pppppppp/8/8/8/8/8/RNBQKBNR w KQkq - 0 1",
            "rnbqkb1r/pppppp1p/5np1/8/2P5/8/PP1PPP1P/RNBQKBNR w KQkq - 2 3"
        };
        Arrays.stream(fens).forEach(f -> System.out.println(Board.fromFEN(f)));
    }
}