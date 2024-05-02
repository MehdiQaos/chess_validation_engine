package dev.mehdi;

public class Chess {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player turn;
    private GameState state;

    public Chess() {
        board = new Board();
        board.init();
        whitePlayer = new Player(Color.WHITE);
        blackPlayer = new Player(Color.BLACK);
        state = GameState.NOT_STARTED;
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

    Response move(Position from, Position to) {
        if (!isPlaying()) {
            return Response.invalid();
        }

        Player player = turn;
        Player opponent = otherPlayer();
    }
}
