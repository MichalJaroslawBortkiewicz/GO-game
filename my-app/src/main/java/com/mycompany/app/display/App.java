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
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 3);
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
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
        return gameScene;
    }

    public void endGame() {
        stage.setScene(menu);
    }

    public static void main( String[] args ) {
        launch(args);
    }
}
