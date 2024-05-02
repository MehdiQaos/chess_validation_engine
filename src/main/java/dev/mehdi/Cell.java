package dev.mehdi;

public class Cell {
    Piece piece;
    Position position;

    Cell() {}

    Cell(Piece piece, Position position) {
        this.piece = piece;
        this.position = position;
    }
}