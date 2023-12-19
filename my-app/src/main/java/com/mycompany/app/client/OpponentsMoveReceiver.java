package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.IOException;

import com.mycompany.app.display.AppManager;

public class OpponentsMoveReceiver implements Runnable {
    DataInputStream input;
    int size;

    @Override
    public void run() {
        char[][] board = new char[size][size];

        try {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    board[i][j] = input.readChar();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        AppManager.getInstance().getGameScene().rearrange(board);
        AppManager.getInstance().setMyTurn(true);
    }
    
    public OpponentsMoveReceiver(DataInputStream input, int size) {
        this.input = input;
        this.size = size;
    }
}
