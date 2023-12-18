package com.mycompany.app.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    Client client;


    @Override
    public void start(Stage stage) {
        stage.setTitle("GO");

        final Button gameButton9noBot = new Button("Play 9x9 against Player");
        final Button gameButton13noBot = new Button("Play 13x13 against Player");
        final Button gameButton19noBot = new Button("Play 19x19 against Player");
        final Button gameButton9withBot = new Button("Play 9x9 against Bot");
        final Button gameButton13withBot = new Button("Play 13x13 against Bot");
        final Button gameButton19withBot = new Button("Play 19x19 against Bot");

        gameButton9noBot.setOnAction(new GameButtonHandler(9, false));
        gameButton13noBot.setOnAction(new GameButtonHandler(13, false));
        gameButton19noBot.setOnAction(new GameButtonHandler(19, false));
        gameButton9withBot.setOnAction(new GameButtonHandler(9, true));
        gameButton13withBot.setOnAction(new GameButtonHandler(13, true));
        gameButton19withBot.setOnAction(new GameButtonHandler(19, true));

        final GridPane inputGridPane = new GridPane();

        GridPane.setConstraints(gameButton9noBot, 0, 0);
        GridPane.setConstraints(gameButton13noBot, 1, 0);
        GridPane.setConstraints(gameButton19noBot, 2, 0);
        GridPane.setConstraints(gameButton9withBot, 0, 1);
        GridPane.setConstraints(gameButton13withBot, 1, 1);
        GridPane.setConstraints(gameButton19withBot, 2, 1);
        
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(
            gameButton9noBot,
            gameButton13noBot,
            gameButton19noBot,
            gameButton9withBot,
            gameButton13withBot,
            gameButton19withBot
        );

        final Group rootGroup = new Group();

        rootGroup.getChildren().add(inputGridPane);

        Scene menu = new Scene(rootGroup);

        stage.setScene(menu);
        stage.setX(0);
        stage.setY(5);
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight()-5);
        stage.setMaximized(true);
        stage.show();
    }

    final class GameButtonHandler implements EventHandler<ActionEvent> {
        private final int boardSize;
        private final boolean withBot;

        public GameButtonHandler(int boardSize, boolean withBot) {
            this.boardSize = boardSize;
            this.withBot = withBot;
        }

        @Override
        public void handle(ActionEvent event) {
            client = new Client(boardSize, withBot);
        }
    }

    public static void main( String[] args ) {
        launch(args);
    }
}
