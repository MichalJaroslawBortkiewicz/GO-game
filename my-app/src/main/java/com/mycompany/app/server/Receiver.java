package com.mycompany.app.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver implements Runnable {
    private Session session;
    private DataInputStream inputStream;
    private int player;
    private boolean gamesON;

    @Override
    public void run() {
        while(gamesON) {
            try {
                int x = inputStream.readInt();
                int y = inputStream.readInt();

                System.out.println("Received move: " + x + " " + y + " from player " + player);

                synchronized (session) {
                    session.addStone(x, y, player);
                    session.notifyAll();
                }
            }
            catch (IOException ex) {
                System.err.println(ex.getMessage());
                //TODO : surrender
                break;
            }
        }
    }

    public void gamesOFF() {
        gamesON = false;
    }

    public Receiver(Session session, Socket inputStream, int player) {
        this.session = session;
        try {
            this.inputStream = new DataInputStream(inputStream.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        this.player = player;
        this.gamesON = true;
    }
}
