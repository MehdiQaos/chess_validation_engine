package dev.mehdi;

import dev.mehdi.piece.Piece;

public class Cell {
    Piece piece;
    Position position;

    Cell() {}

    Cell(Piece piece, Position position) {
        this.piece = piece;
        this.position = position;
    }
}