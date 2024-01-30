package com.mycompany.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CancelationReceiver implements Runnable {
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private Server server;
    private int index;

    @Override
    public void run() {
        try {
            if (inputStream.readBoolean()) {
                return;
            }

            outputStream.writeBoolean(false);
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        synchronized (server) {
            server.cancelSession(index);
        }
    }

    public CancelationReceiver(DataOutputStream outputStream, DataInputStream inputStream, Server server, int index) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.server = server;
        this.index = index;
    }
}
