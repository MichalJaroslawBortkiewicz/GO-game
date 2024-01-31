package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.IOException;

import com.mycompany.app.display.AppManager;

import javafx.application.Platform;

public class FinalReceiver implements Runnable {
    private DataInputStream input;

    @Override
    public void run() {
        System.out.println("waiting for decision");
        try {
            if (input.readBoolean()) {
                System.out.println("opponent declined");
                AppManager.getInstance().setMyTurn(false);
                Platform.runLater(() -> AppManager.getInstance().exitPropositionMode());
                AppManager.getInstance().waitForOpponentsMove();
            } else {
                System.out.println("opponent accpeted");
                System.out.println(input.readInt());
                Platform.runLater(() -> AppManager.getInstance().endGame());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public FinalReceiver(DataInputStream input) {
        this.input = input;
    }
}
