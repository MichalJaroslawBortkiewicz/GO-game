package com.mycompany.app.display;

import javafx.application.Application;
import javafx.scene.Scene;
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
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight()-5);
        stage.setMaximized(true);
        stage.show();
    }

    public GameScene startGame(int size) {
        GameScene gameScene = new GameScene(size);
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
