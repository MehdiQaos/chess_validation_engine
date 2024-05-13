package dev.mehdi;

import dev.mehdi.piece.Piece;
import lombok.NonNull;

public class Player {
    Color color;
    boolean kingMoved;
    Piece king;

    Player(@NonNull Color color) {
        this.color = color;
        kingMoved = false;
    }

    void setKing(Piece king) {
        this.king = king;
    }
}
