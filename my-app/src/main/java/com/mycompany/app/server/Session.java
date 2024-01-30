package com.mycompany.app.server;

public interface Session extends Runnable {
    void startGame();
    void endGame();
    void addStone(int x, int y, int player);
    void setProposition(char[][] proposition);
}
