package dev.mehdi;

public class Board {
    private Cell[][] cells = new Cell[8][8];

    void init() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(null, new Position(i, j));
            }
        }

        for (int i = 0; i < 8; i++) {
            cells[1][i].piece = new Piece(Color.WHITE, Type.PAWN);
            cells[6][i].piece = new Piece(Color.BLACK, Type.PAWN);
        }

        cells[0][0].piece = new Piece(Color.WHITE, Type.ROOK);
        cells[0][7].piece = new Piece(Color.WHITE, Type.ROOK);
        cells[7][0].piece = new Piece(Color.BLACK, Type.ROOK);
        cells[7][7].piece = new Piece(Color.BLACK, Type.ROOK);

        cells[0][1].piece = new Piece(Color.WHITE, Type.KNIGHT);
        cells[0][6].piece = new Piece(Color.WHITE, Type.KNIGHT);
        cells[7][1].piece = new Piece(Color.BLACK, Type.KNIGHT);
        cells[7][6].piece = new Piece(Color.BLACK, Type.KNIGHT);

        cells[0][2].piece = new Piece(Color.WHITE, Type.BISHOP);
        cells[0][5].piece = new Piece(Color.WHITE, Type.BISHOP);
        cells[7][2].piece = new Piece(Color.BLACK, Type.BISHOP);
        cells[7][5].piece = new Piece(Color.BLACK, Type.BISHOP);

        cells[0][3].piece = new Piece(Color.WHITE, Type.QUEEN);
        cells[7][3].piece = new Piece(Color.BLACK, Type.QUEEN);

        cells[0][4].piece = new Piece(Color.WHITE, Type.KING);
        cells[7][4].piece = new Piece(Color.BLACK, Type.KING);
    }

    void move(Position from, Position to) {
        Cell fromCell = get(from);
        Cell toCell = get(to);
        toCell.piece = fromCell.piece;
        fromCell.piece = null;
    }

    Cell get(Position pos) {
        validatePosition(pos);
        return cells[pos.row - 1][pos.col - 1];
    }

    void validatePosition(Position pos) {
        if (pos.row < 0 || pos.row > 7 || pos.col < 0 || pos.col > 7)
            throw new RuntimeException("Invalid position");
    }
}
