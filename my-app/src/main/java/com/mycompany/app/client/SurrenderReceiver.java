package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.IOException;

import com.mycompany.app.display.AppManager;

import javafx.application.Platform;

public class SurrenderReceiver implements Runnable {
    private DataInputStream input;

    @Override
    public void run() {
        try {
            if (input.readBoolean()) {
                System.out.println("Opponent resigned");
                Platform.runLater( () -> AppManager.getInstance().endGame());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public SurrenderReceiver(DataInputStream input) {
        this.input = input;
    }
}
