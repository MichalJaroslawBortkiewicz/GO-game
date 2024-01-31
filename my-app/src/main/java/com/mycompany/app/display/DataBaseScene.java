package com.mycompany.app.display;

import java.io.IOException;

import com.mycompany.app.board.GameManager;
import com.mycompany.app.client.exceptions.FromServerException;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class DataBaseScene extends Group{
    private Field[][] boardData;
    private int size;

    private int moveNr = 0;

    private int borderWidth = 20;
    private int gridWidth = 40;


    DataBaseScene(int size){
        this.size = size;
        makeScene();
    }
    
    
    private void makeScene(){
        this.boardData = new Field[size][size];
        int boardLength = 2 * borderWidth + gridWidth * (size - 1);

        Group lines = new Group();
        Group stones = new Group();

        Rectangle background = new Rectangle(Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        background.setFill(Color.DARKKHAKI);

        Rectangle board = new Rectangle(boardLength, boardLength);
        board.setFill(Color.KHAKI);
        board.setStroke(Color.BROWN);
        board.setStrokeWidth(3);

        Button prevMoveButton = new Button("Prev Move");
        prevMoveButton.setLayoutX(size * 40 + 30);
        prevMoveButton.setLayoutY(30);
        prevMoveButton.setOnAction(event -> {
            if(moveNr == 0){return;}
            moveNr--;
            try{
                char[][] boardState = AppManager.getInstance().sendMoveNr(moveNr);
                rearrange(boardState); 
            }catch(FromServerException ex){
                moveNr++;
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });

        Button nextMoveButton = new Button("Next Move");
        nextMoveButton.setLayoutX(size * 40 + 30);
        nextMoveButton.setLayoutY(60);
        nextMoveButton.setOnAction(event -> {
            moveNr++;
            try{
                char[][] boardState = AppManager.getInstance().sendMoveNr(moveNr);
                rearrange(boardState); 
            }catch(FromServerException ex){
                moveNr--;
            }catch(IOException ex){
                ex.printStackTrace();
            }
        });

        Button prevGameButton = new Button("Prev Game");
        prevGameButton.setLayoutX(size * 40 + 30);
        prevGameButton.setLayoutY(90);
        prevGameButton.setOnAction(event -> {
            prevGameScene();
        });

        Button nextGameButton = new Button("Next Game");
        nextGameButton.setLayoutX(size * 40 + 30);
        nextGameButton.setLayoutY(120);
        nextGameButton.setOnAction(event -> {
            nextGameScene();
        });


        Button returnButton = new Button("Return");
        returnButton.setLayoutX(size * 40 + 30);
        returnButton.setLayoutY(150);
        returnButton.setOnAction(event -> {
        });



        for(int i = 0; i < size; i++) {
            double start = borderWidth;
            double end = start + (size - 1) * gridWidth;
            double shift = borderWidth + i * gridWidth;

            Line vLine = new Line(shift, start, shift, end);
            Line hLine = new Line(start, shift, end, shift);

            vLine.setStrokeWidth(2);
            hLine.setStrokeWidth(2);


            lines.getChildren().add(vLine);
            lines.getChildren().add(hLine);


            for(int j = 0; j < size; j++) {
                Field field = new Field(i, j, 0.4 * gridWidth);
                stones.getChildren().add(field);
                boardData[i][j] = field;
            }
        }

        getChildren().addAll(background, board, lines, stones, prevMoveButton, nextMoveButton, prevGameButton, nextGameButton, returnButton);
    }

    public void nextGameScene(){
        this.size = AppManager.getInstance().nextGame();
        GameManager.getInstance().startGame(size);
        getChildren().clear();
        moveNr = 0;
        System.out.println(size);
        makeScene();

        System.out.println(size);
    }

    public void prevGameScene(){
        this.size = AppManager.getInstance().prevGame();
        GameManager.getInstance().startGame(size);
        getChildren().clear();
        moveNr = 0;
        makeScene();

        System.out.println(size);
    }

    
    public void rearrange(char[][] boardDataState) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                String colorName = String.valueOf(boardDataState[i][j]);
                boardData[i][j].changeColor(colorName);
            }
        }
    }

    private final class Field extends Circle {
        FieldColor fieldColor = FieldColor.E;
        
        public Field(int x, int y, double r) {
            setRadius(r);
            setCenterX(borderWidth + x * gridWidth);
            setCenterY(borderWidth + y * gridWidth);
            setFill(Color.TRANSPARENT);

            setOpacity(0);
            setStrokeWidth(2);
            setStroke(Color.gray(0.2));
        }


        private void changeColor(String colorName){
            fieldColor = FieldColor.valueOf(colorName);
            setFill(fieldColor.getColor());

            if(FieldColor.E.equals(fieldColor)){
                setOpacity(0);
                return;
            }
            
            setOpacity(1);
        }
    } 
}
