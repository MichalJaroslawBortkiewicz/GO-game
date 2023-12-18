package com.mycompany.app.server;

import java.net.Socket;

public class Session implements Runnable {
    Socket firstPlayer;
    Socket secondPlayer;


    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
    public Session(Socket firstPlayer, Socket secondPlayer)
    {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
}
