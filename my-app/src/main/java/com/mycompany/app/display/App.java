package com.mycompany.app.display;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    private Stage stage;
    private Scene menu;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        AppManager.getInstance().setApp(this);

        stage.setTitle("GO");

        menu = new Scene(new MenuScene());

        stage.setScene(menu);
        stage.setX(0);
        stage.setY(5);
        stage.setWidth(400);
        stage.setHeight(400);
        stage.setOnCloseRequest(event -> AppManager.getInstance().surrender());
        //stage.setMaximized(true);
        stage.show();
    }

    public GameScene startGame(int size) {
        GameScene gameScene = new GameScene(size);
        gameScene.addEventFilter(MouseEvent.DRAG_DETECTED , new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameScene.startFullDrag();
            }
        });
        stage.setScene(new Scene(gameScene));
        stage.setWidth(size * 40 + 150);
        stage.setHeight(size * 40 + 40);
        return gameScene;
    }

    public void endGame() {
        stage.setWidth(400);
        stage.setHeight(400);
        stage.setScene(menu);
    }

    public static void main( String[] args ) {
        launch(args);
    }
}
