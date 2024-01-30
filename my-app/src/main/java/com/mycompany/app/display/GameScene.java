package com.mycompany.app.display;


import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class GameScene extends Group {
    private final Field[][] boardData;
    private final int size;

    private PlayerColor playerColor;

    private int borderWidth = 20;
    private int gridWidth = 40;

    
    public GameScene(int size) {
        this.size = size;
        this.boardData = new Field[size][size];
        
        int boardLength = 2 * borderWidth + gridWidth * (size - 1);

        Group lines = new Group();
        Group stones = new Group();

        
        Rectangle board = new Rectangle(boardLength, boardLength);
        board.setFill(Color.KHAKI);
        board.setStroke(Color.BROWN);
        board.setStrokeWidth(3);

        Button passButton = new Button("Pass");
        passButton.setLayoutX(400);
        passButton.setLayoutY(30);
        passButton.setOnAction(event -> {
            if (AppManager.getInstance().sendMove(-1, -1) != null) {
                AppManager.getInstance().waitForOpponentsMove();
            }
        });
        Button resignButton = new Button("Resign");
        resignButton.setLayoutX(400);
        resignButton.setLayoutY(80);
        resignButton.setOnAction(event -> AppManager.getInstance().surrender());

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

        getChildren().addAll(board, lines, stones, passButton, resignButton);

        if(AppManager.getInstance().isMyTurn()){
            playerColor = PlayerColor.BLACK;
        }
        else {
            playerColor = PlayerColor.WHITE;
        }
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
        int x;
        int y;

       FieldColor fieldColor = FieldColor.E;
        
        public Field(int x, int y, double r) {
            this.x = x;
            this.y = y;

            setRadius(r);
            setCenterX(borderWidth + x * gridWidth);
            setCenterY(borderWidth + y * gridWidth);
            setFill(Color.TRANSPARENT);
            setOpacity(0);
            setStrokeWidth(2);
            setStroke(Color.gray(0.2));
            
            setOnMouseEntered(event -> {
                if(!FieldColor.E.equals(fieldColor)){ return; }
                setOpacity(0.5);
                setFill(playerColor.getColor());
            });

            setOnMouseExited(event -> {
                if(!FieldColor.E.equals(fieldColor)){ return; }
                setFill(fieldColor.getColor());
                setOpacity(0);
            });

            setOnMouseClicked(event -> sendMove());
        }

        private void sendMove() {
            char[][] boardDataState = AppManager.getInstance().sendMove(x, y);
            if (boardDataState != null) {
                rearrange(boardDataState);
                AppManager.getInstance().waitForOpponentsMove();
            }
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

    private enum FieldColor{
        B(Color.BLACK),
        W(Color.WHITE),
        E(Color.TRANSPARENT);

        private Paint color;

        FieldColor(Color color){
            this.color = color;
        }

        public Paint getColor() {
            return color;
        }
    } 
}
