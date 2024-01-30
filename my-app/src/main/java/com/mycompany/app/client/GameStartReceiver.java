package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.IOException;

import com.mycompany.app.display.AppManager;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class GameStartReceiver implements Runnable {
    private DataInputStream input;
    private Dialog<ButtonType> dialog;
    private boolean killed = false;

    @Override
    public void run() {
        try {
            AppManager.getInstance().setMyTurn(input.readBoolean());
            if (killed) {
                System.out.println("killed");
                return;
            }
            AppManager.getInstance().setGameCanceled(false);
            Platform.runLater( () -> dialog.close() );
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void kill() {
        killed = true;
    }
    
    public GameStartReceiver(DataInputStream input, Dialog<ButtonType> dialog) {
        this.input = input;
        this.dialog = dialog;
    }
}
