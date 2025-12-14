package dev.mehdi;

public interface IChessGame {
    Response move(String move);
    Response move(String from, String to);
    Response move(Position from, Position to);
}