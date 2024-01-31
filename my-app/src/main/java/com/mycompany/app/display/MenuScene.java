package com.mycompany.app.display;

import java.io.IOException;

import com.mycompany.app.client.Client;
import com.mycompany.app.client.exceptions.FromServerException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class MenuScene extends Group {
    Dialog<ButtonType> dialog = new Dialog<ButtonType>();

    public MenuScene()
    {
        dialog.setTitle("Wait");
        ButtonType type = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.setContentText("Waiting for another player");
        dialog.getDialogPane().getButtonTypes().add(type);

        Rectangle background = new Rectangle(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        background.setFill(Color.DARKKHAKI);

        final Button gameButton9noBot = new Button("Player  9x9 ");
        final Button gameButton13noBot = new Button("Player 13x13");
        final Button gameButton19noBot = new Button("Player 19x19");
        final Button gameButton9withBot = new Button("Bot  9x9");
        final Button gameButton13withBot = new Button("Bot 13x13");
        final Button gameButton19withBot = new Button("Bot 19x19");

        final Button dataBaseButton = new Button("archived games");

        gameButton9noBot.setOnAction(new GameButtonHandler(9, 0));
        gameButton13noBot.setOnAction(new GameButtonHandler(13, 0));
        gameButton19noBot.setOnAction(new GameButtonHandler(19, 0));
        gameButton9withBot.setOnAction(new GameButtonHandler(9, 1));
        gameButton13withBot.setOnAction(new GameButtonHandler(13, 1));
        gameButton19withBot.setOnAction(new GameButtonHandler(19, 1));

        dataBaseButton.setOnAction(new DataBaseButtonHandler(9, 2));
        final GridPane inputGridPane = new GridPane();
        
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);

        inputGridPane.add(gameButton9noBot, 0, 0);
        inputGridPane.add(gameButton13noBot, 0, 1);
        inputGridPane.add(gameButton19noBot, 0, 2);
        inputGridPane.add(gameButton9withBot, 1, 0);
        inputGridPane.add(gameButton13withBot, 1, 1);
        inputGridPane.add(gameButton19withBot, 1, 2);
        inputGridPane.add(dataBaseButton, 2, 0);
        getChildren().addAll(background, inputGridPane);
    }


    final class DataBaseButtonHandler implements EventHandler<ActionEvent> {
        private final int boardSize;
        private final int type;


        public DataBaseButtonHandler(int boardSize, int type){
            this.boardSize = boardSize;
            this.type = type;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                AppManager.getInstance().setClient(new Client(boardSize, type));
                AppManager.getInstance().startDataBase(boardSize);
            } catch (IOException ex) {
                System.err.println("Connection with server failed: " + ex.getMessage());
            } catch (FromServerException ex) {
                System.err.println("Server send exception: " + ex.getMessage());
            }
        }
    }

    final class GameButtonHandler implements EventHandler<ActionEvent> {
        private final int boardSize;
        private final int type;

        public GameButtonHandler(int boardSize, int type) {
            this.boardSize = boardSize;
            this.type = type;
        }

        @Override
        public void handle(ActionEvent event) {

            try {
                AppManager.getInstance().setClient(new Client(boardSize, type));
                AppManager.getInstance().setGameCanceled(true);
                AppManager.getInstance().waitForGameStart(dialog);
                System.out.println(dialog.showAndWait());
                if (AppManager.getInstance().isGameCanceled()) {
                    AppManager.getInstance().cancelGame();
                } else {
                    AppManager.getInstance().startGame(boardSize);
                }
            } catch (IOException ex) {
                System.err.println("Connection with server failed: " + ex.getMessage());
            } catch (FromServerException ex) {
                System.err.println("Server send exception: " + ex.getMessage());
            }
        }
    }
}
