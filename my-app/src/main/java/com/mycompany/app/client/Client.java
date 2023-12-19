package com.mycompany.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.mycompany.app.client.exceptions.FromServerException;

public class Client {
    Socket socket;

    private DataInputStream fromServer;
    private DataOutputStream toServer;

    private int size;

    public char[][] sendMove(int x, int y) throws IOException, FromServerException{
        toServer.writeInt(x);
        toServer.writeInt(y);

        if (fromServer.readBoolean()) {
            throw new FromServerException(fromServer.readAllBytes());
        }

        char[][] board = new char[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board[i][j] = fromServer.readChar();
            }
        }

        return board;
    }

    public Client(int size, boolean withBot) throws IOException, FromServerException {
        this.size = size;

        socket = new Socket("localhost", 8000);
        fromServer = new DataInputStream(socket.getInputStream());
        toServer = new DataOutputStream(socket.getOutputStream());

        toServer.writeInt(size);
        toServer.writeBoolean(withBot);

        if (fromServer.readBoolean()) {
            throw new FromServerException(fromServer.readAllBytes());
        }
    }
}

