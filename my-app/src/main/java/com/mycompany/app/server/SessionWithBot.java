package com.mycompany.app.server;

import java.net.Socket;

public class SessionWithBot implements Session {
    private Socket player;
    private int size;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    @Override
    public void startGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public void addStone(int x, int y, int player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addStone'");
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }
    
    public SessionWithBot(Socket player, int size)
    {
        this.player = player;
        this.size = size;
    }

    @Override
    public void setProposition(char[][] proposition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProposition'");
    }
}
