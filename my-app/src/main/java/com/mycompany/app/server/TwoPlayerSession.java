package com.mycompany.app.server;

import java.net.Socket;

public class TwoPlayerSession implements Session {
    Socket firstPlayer;
    Socket secondPlayer;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    @Override
    public void startGame(int size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }
    
    public TwoPlayerSession(Socket firstPlayer, Socket secondPlayer, int size)
    {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        startGame(size);
    }
}
