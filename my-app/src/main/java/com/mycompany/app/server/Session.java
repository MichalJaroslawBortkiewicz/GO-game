package com.mycompany.app.server;

public interface Session extends Runnable {
    void startGame(int size);
}
