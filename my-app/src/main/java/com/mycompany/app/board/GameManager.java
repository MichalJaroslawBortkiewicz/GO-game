package com.mycompany.app.board;

public class GameManager {
    private static ThreadLocal<GameManager> instance = new ThreadLocal<GameManager>() {
        @Override
        protected GameManager initialValue() {
            return new GameManager();
        }
    };

    private Board board;
    private int whitePoints = 0;
    private int blackPoints = 0;
    private boolean whitePlays = false;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addWhitePoints(int points) {
        whitePoints += points;
    }

    public int getWhitePoints() {
        return whitePoints;
    }

    public void addBlackPoints(int points) {
        blackPoints += points;
    }

    public int getBlackPoints() {
        return blackPoints;
    }

    public void nextPlayer() {
        whitePlays = !whitePlays;
    }

    public static GameManager getInstance() {
        return instance.get();
    }

    public static void resetInstance() {
        instance.remove();
    }
}
