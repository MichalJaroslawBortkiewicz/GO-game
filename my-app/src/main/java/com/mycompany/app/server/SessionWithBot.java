package com.mycompany.app.server;

import java.net.Socket;

public class SessionWithBot implements Session {
    Socket player;

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
    
    public SessionWithBot(Socket player, int size)
    {
        this.player = player;
    }
}
