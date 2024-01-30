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

    private Button passButton;
    private Button resignButton;

    private Button sendButton;
    private Button whiteButton;
    private Button blackButton;
    private Button eraserButton;
    private FieldColor currentColor = FieldColor.E;
    private char [][] proposition;
    Group propositionGroup = new Group();

    private Button acceptButton;
    private Button declineButton;
    
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

        passButton = new Button("Pass");
        passButton.setLayoutX(size * 40 + 30);
        passButton.setLayoutY(30);
        passButton.setOnAction(event -> {
            char[][] boardDataState = AppManager.getInstance().sendMove(-1, -1);
            if (boardDataState != null) {
                if (boardDataState[0][0] == '\1') {
                    AppManager.getInstance().enterJudgeMode();
                    // TODO double pass
                }
                AppManager.getInstance().waitForOpponentsMove();
            }
        });
        resignButton = new Button("Resign");
        resignButton.setLayoutX(size * 40 + 30);
        resignButton.setLayoutY(80);
        resignButton.setOnAction(event -> AppManager.getInstance().surrender());

        sendButton = new Button("Send");
        sendButton.setLayoutX(size * 40 + 30);
        sendButton.setLayoutY(30);
        sendButton.setOnAction(event -> AppManager.getInstance().sendProposition(proposition));
        Rectangle rect = new Rectangle(10, 10);
        rect.setFill(Color.WHITE);
        whiteButton = new Button("", rect);
        whiteButton.setLayoutX(size * 40 + 30);
        whiteButton.setLayoutY(80);
        whiteButton.setOnAction(event -> currentColor = FieldColor.W);
        rect = new Rectangle(10, 10);
        rect.setFill(Color.BLACK);
        blackButton = new Button("", rect);
        blackButton.setLayoutX(size * 40 + 60);
        blackButton.setLayoutY(80);
        blackButton.setOnAction(event -> currentColor = FieldColor.B);
        eraserButton = new Button("   ");
        eraserButton.setLayoutX(size * 40 + 90);
        eraserButton.setLayoutY(80);
        eraserButton.setOnAction(event -> currentColor = FieldColor.E);
        proposition = new char[size][size];

        acceptButton = new Button("Accept");
        acceptButton.setLayoutX(size * 40 + 30);
        acceptButton.setLayoutY(30);
        acceptButton.setOnAction(event -> AppManager.getInstance().sendDecision(true));
        declineButton = new Button("Decline");
        declineButton.setLayoutX(size * 40 + 30);
        declineButton.setLayoutY(80);
        declineButton.setOnAction(event -> AppManager.getInstance().sendDecision(false));

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
                
                PropositionField proField = new PropositionField(i, j, 0.4 * gridWidth);
                propositionGroup.getChildren().add(proField);
                proposition[i][j] = 'E';
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

    public void enterProposingMode() {
        getChildren().removeAll(passButton, resignButton);
        getChildren().addAll(sendButton, whiteButton, blackButton, eraserButton, propositionGroup);
    }

    public void enterJudgeMode() {
        getChildren().removeAll(passButton, resignButton);
        getChildren().addAll(acceptButton, declineButton, propositionGroup);
    }

    private final class PropositionField extends Rectangle {

        FieldColor fieldColor = FieldColor.E;

        public PropositionField(int x, int y, double r) {
            
            setHeight(2.4*r);
            setWidth(2.4*r);
            setLayoutX(borderWidth + x * gridWidth - 1.2*r);
            setLayoutY(borderWidth + y * gridWidth - 1.2*r);
            setFill(Color.TRANSPARENT);
            setOpacity(0.5);
            setOnMouseDragEntered(event -> {
                fieldColor = currentColor;
                setFill(fieldColor.getColor());
                proposition[x][y] = fieldColor.toChar();
            });
            setOnMouseClicked(event -> {
                fieldColor = currentColor;
                setFill(fieldColor.getColor());
                proposition[x][y] = fieldColor.toChar();
            });
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

        public char toChar() {
            if (FieldColor.B.equals(this)){
                return 'B';
            }
            if (FieldColor.W.equals(this)){
                return 'W';
            }
            if (FieldColor.E.equals(this)){
                return 'E';
            }
            return '\0';
        }
    } 
}
