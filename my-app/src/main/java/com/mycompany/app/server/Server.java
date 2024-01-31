package com.mycompany.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.mycompany.app.database.IDataBaseManager;
import com.mycompany.app.database.WrongGameNumberException;
import com.mycompany.app.database.DataBaseManager;


public final class Server {
    private int sessions[] = {0, 0, 0};

    private String dbURL = "jdbc:mysql://localhost:3306/go_games";
    private String login = "root";
    private String password = "Rodzina4";

    public void cancelSession(int index) {
        sessions[index] = 0;
    }

    public static void main(String[] args) {
        new Server();
    }

    @SuppressWarnings({"resource", "PMD.CloseResource"})
    private Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println(new Date() + ":     Server started at socket 8000");
            int sessionNum = 1;
            Socket players[] = {null, null, null};
            while (true) {
                System.out.println(new Date() + ":     Waiting for players");

                Socket player = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(player.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(player.getOutputStream());
                int size = inputStream.readInt();
                int type = inputStream.readInt();
                int index;
                switch (size) {
                case 9:
                    index = 0;
                    break;
                case 13:
                    index = 1;
                    break;
                case 19:
                    index = 2;
                    break;
                default:
                    index = -1;
                    break;
                }
                if (index == -1) {
                    outputStream.writeBoolean(true);
                    outputStream.writeInt("Invalid board size!".getBytes().length);
                    outputStream.writeBytes("Invalid board size!");
                    continue;
                }
                if (type == 1) {
                    System.out.println(new Date() + ":     Player joined session " + sessionNum + " and they're playing with bot. Their IP address is " + player.getLocalAddress().getHostAddress());
                    IDataBaseManager dataBaseManager = new DataBaseManager(dbURL, login, password);
                    Session task = new SessionWithBot(player, size, dataBaseManager);
                    System.out.println(new Date() + ":     Starting a thread for session " + sessionNum++ + "...");
                    Thread thread = new Thread(task);
                    thread.start();
                    outputStream.writeBoolean(false);
                } else if (type == 2) {
                    //TODO: implement game changeing
                    try {
                        IDataBaseManager dataBaseManager = new DataBaseManager(dbURL, login, password, 1);
                        System.out.println(new Date() + ":     Player joined session " + sessionNum + " and they're replaying game from database. Their IP address is " + player.getLocalAddress().getHostAddress());
                        Session task = new SessionFromDatabase(player, size, dataBaseManager);
                        System.out.println(new Date() + ":     Starting a thread for session " + sessionNum++ + "...");
                        Thread thread = new Thread(task);
                        thread.start();
                        outputStream.writeBoolean(false);
                        
                    } catch (WrongGameNumberException ex) {
                        outputStream.writeBoolean(true);
                    }
                }
                else if (sessions[index] == 0) {
                    System.out.println(new Date() + ":     first player joined session " + sessionNum + ". Their IP address is " + player.getLocalAddress().getHostAddress());
                    sessions[index] = sessionNum++;
                    players[index] = player;
                    CancelationReceiver receiver = new CancelationReceiver(outputStream, inputStream, this, index);
                    Thread thread = new Thread(receiver);
                    thread.start();
                    outputStream.writeBoolean(false);
                }
                else {
                    IDataBaseManager dataBaseManager = new DataBaseManager(dbURL, login, password);
                    Session task = new TwoPlayerSession(players[index], player, size, dataBaseManager);
                    System.out.println(new Date() + ":     Starting a thread for session " + sessions[index] + "...");
                    Thread thread = new Thread(task);
                    thread.start();
                    sessions[index] = 0;
                    players[index] = null;
                    outputStream.writeBoolean(false);
                    inputStream.readBoolean();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
