package com.mycompany.app.display;

import java.io.IOException;

import com.mycompany.app.client.Client;
import com.mycompany.app.client.exceptions.FromServerException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    private static App instance;

    private boolean myTurn;
    private Stage stage;
    private Scene menu;
    private Client client;

    @Override
    public void start(Stage stage) {
        instance = this;
        this.stage = stage;

        stage.setTitle("GO");

        menu = new Scene(new MenuScene());

        stage.setScene(menu);
        stage.setX(0);
        stage.setY(5);
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight()-5);
        stage.setMaximized(true);
        stage.show();
    }

    public void startGame(int size) {
        stage.setScene(new Scene(new GameScene(size)));
    }

    public void endGame() {
        stage.setScene(menu);
    }

    @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
    public char[][] sendMove(int i, int j)
    {
        if (!myTurn) {
            return null;
        }
        char[][] boardState = null;
        try {
            boardState = client.sendMove(i, j);
            myTurn = false;
        } catch (IOException ex) {
            System.err.println("Connection with server failed: " + ex.getMessage());
        } catch (FromServerException ex) {
            System.err.println("Server send exception: " + ex.getMessage());
        }
        return boardState;
    }

    public void setClient(Client client) {
        instance.client = client;
    }

    public Client getClient() {
        return instance.client;
    }

    public static App getApp() {
        return instance;
    }

    public static void main( String[] args ) {
        launch(args);
    }
}
