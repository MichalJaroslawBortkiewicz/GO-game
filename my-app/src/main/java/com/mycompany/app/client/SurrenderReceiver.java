package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.IOException;

public class SurrenderReceiver implements Runnable {
    private DataInputStream input;

    @Override
    public void run() {
        try {
            if (input.readBoolean()) {
                // TODO : opponent surrendered
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public SurrenderReceiver(DataInputStream input) {
        this.input = input;
    }
}
