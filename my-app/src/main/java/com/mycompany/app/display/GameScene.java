package com.mycompany.app.display;

import java.io.IOException;

import com.mycompany.app.client.Client;
import com.mycompany.app.client.exceptions.FromServerException;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameScene extends Group {
    final Field[][] board;
    final int size;

    private void rearrange(char[][] boardState) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                switch (boardState[i][j]) {
                case 'W':
                    board[i][j].setFill(Color.WHITE);
                    break;
                case 'B':
                    board[i][j].setFill(Color.BLACK);
                    break;
                default:
                    board[i][j].setFill(Color.GREY);
                    break;
                }
            }
        }
    }

    public GameScene(int size) {
        this.size = size;
        this.board = new Field[size][size];
        final GridPane pane = new GridPane();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Field field = new Field(i, j, 300f/size);
                pane.add(field, i, j);
                board[i][j] = field;
            }
        }

        getChildren().add(pane);
    }

    final class Field extends Circle {
        int i;
        int j;
        Client client;

        private void sendMove() {
            setFill(Color.BLACK);
            /*try {
                rearrange(client.sendMove(i, j));
            } catch (IOException ex) {
                System.err.println("Connection with server failed: " + ex.getMessage());
            } catch (FromServerException ex) {
                System.err.println("Server send exception: " + ex.getMessage());
            }*/
        }

        public Field(int i, int j, float r) {
            this.i = i;
            this.j = j;
            client = App.getClient();
            setRadius(r);
            setFill(Color.GRAY);
            setOnMouseClicked(event -> sendMove());
        }
    }
}
