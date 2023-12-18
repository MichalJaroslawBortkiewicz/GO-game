package com.mycompany.app.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public final class Server {

    public static final int WAIT = 0;
    public static final int BLACKPLAYER = 1;
    public static final int WHITEPLAYER = 2;

    public static void main(String[] args) {
        new Server();
    }

    @SuppressWarnings({"resource", "PMD.CloseResource"})
    private Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println(new Date() + ":     Server started at socket 8000\n");
            int sessionNum = 1;
            while (true) {
                System.out.println(new Date() + ":     Waiting for players to join session " + sessionNum + "\n");

                Socket firstPlayer = serverSocket.accept();
                System.out.println(new Date() + ":     Player 1 joined session " + sessionNum + ". Player 1's IP address " + firstPlayer.getInetAddress().getHostAddress() + "\n");

                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(WAIT);

                Socket secondPlayer = serverSocket.accept();
                System.out.println(new Date() + ":     Player 2 joined session " + sessionNum + ". Player 2's IP address " + secondPlayer.getInetAddress().getHostAddress() + "\n");

                int player1;
                int player2;
                if(Math.random() < 0.5) {
                    player1 = BLACKPLAYER;
                    player2 = WHITEPLAYER;
                }
                else {
                    player1 = WHITEPLAYER;
                    player2 = BLACKPLAYER;
                }
                new DataOutputStream(firstPlayer.getOutputStream()).writeInt(player1);
                new DataOutputStream(secondPlayer.getOutputStream()).writeInt(player2);


                System.out.println(new Date() + ":     Starting a thread for session " + sessionNum++ + "...\n");
                Session task = new Session(firstPlayer, secondPlayer);
                Thread t1 = new Thread(task);
                t1.start();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
