package com.mycompany.app.display;

import java.io.IOException;

import com.mycompany.app.client.Client;
import com.mycompany.app.client.exceptions.FromServerException;

public class AppManager {
    private static AppManager instance;
    
    private App app;
    private boolean myTurn;
    private Client client;
    private GameScene gameScene;

    public void startGame(int size) {
        System.out.println("Waiting for player to join...");
        try {
            myTurn = client.doIStart();
            gameScene = app.startGame(size);
            if(!myTurn) {
                waitForOpponentsMove();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
    public char[][] sendMove(int i, int j)
    {
        if (!myTurn) {
            return null;
        }
        char[][] boardState = null;
        try {
            System.out.println("Asking client to send move");
            boardState = client.sendMove(i, j);
            myTurn = false;
        } catch (IOException ex) {
            System.err.println("Connection with server failed: " + ex.getMessage());
        } catch (FromServerException ex) {
            System.err.println("Server returned exception: " + ex.getMessage());
        }
        return boardState;
    }

    public void waitForOpponentsMove()
    {
        try {
            client.waitForOpponentsMove();
        } catch (IOException ex) {
            System.err.println("Connection with server failed: " + ex.getMessage());
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public void setClient(Client client) {
        instance.client = client;
    }

    public Client getClient() {
        return instance.client;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public synchronized static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }
}
