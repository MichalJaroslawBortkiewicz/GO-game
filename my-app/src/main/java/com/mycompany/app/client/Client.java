package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.mycompany.app.client.exceptions.FromServerException;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class Client {
    Socket socket;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    private GameStartReceiver gameStartReceiver;

    private int size;

    public char[][] sendMove(int x, int y) throws IOException, FromServerException{
        toServer.writeInt(x);
        toServer.writeInt(y);
        System.out.println("Client send move");

        if (fromServer.readBoolean()) {
            System.out.println("Exception from server");
            int length = fromServer.readInt();
            throw new FromServerException(fromServer.readNBytes(length));
        }

        System.out.println("No exception from server");

        char[][] board = new char[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = fromServer.readChar();
            }
        }

        return board;
    }

    public void surrender() throws IOException {
        toServer.writeInt(-2);
        toServer.writeInt(-2);
    }

    public void sendProposition(char[][] proposition) {
        try {
            toServer.writeInt(-1);
            toServer.writeInt(-1);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    toServer.writeChar(proposition[i][j]);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void sendDecision(boolean decision) {
        try {
            toServer.writeInt(-1);
            toServer.writeInt(-1);
            toServer.writeBoolean(decision);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void waitForOpponentsMove() {
        OpponentsMoveReceiver receiver = new OpponentsMoveReceiver(fromServer, size);
        Thread thread = new Thread(receiver);
        thread.start();
    }

    public void waitForGameStart(Dialog<ButtonType> dialog) {
        gameStartReceiver = new GameStartReceiver(fromServer, dialog);
        Thread thread = new Thread(gameStartReceiver);
        thread.start();
    }

    public void waitForOpponentsSurrender() {
        SurrenderReceiver receiver = new SurrenderReceiver(fromServer);
        Thread thread = new Thread(receiver);
        thread.start();
    }

    public void cancelGame() {
        gameStartReceiver.kill();

        try {
            toServer.writeBoolean(false);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void confirmGame() {
        System.out.println("confirm");
        try {
            toServer.writeBoolean(true);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Client(int size, int type) throws IOException, FromServerException {
        this.size = size;

        socket = new Socket("localhost", 8000);
        fromServer = new DataInputStream(socket.getInputStream());
        toServer = new DataOutputStream(socket.getOutputStream());

        toServer.writeInt(size);
        toServer.writeInt(type);

        if (fromServer.readBoolean()) {
            int length = fromServer.readInt();
            throw new FromServerException(fromServer.readNBytes(length));
        }
    }
}

