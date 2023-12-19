package com.mycompany.app.display;

import com.mycompany.app.board.StoneColor;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class GameScene extends Group {
    final Field[][] board;
    final int size;

    final StoneColor color;

    public void rearrange(char[][] boardState) {
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

        Group lines = new Group();
        Group stones = new Group();

        Rectangle rectangle = new Rectangle(655 + size * 5, 655 + size * 5);
        rectangle.setFill(Color.gray(0.7));

        for(int i = 0; i < size; i++) {
            lines.getChildren().add(new Line(15, 30 + 300f/size + (600f/size + 5) * i, 640 + size * 5, 30 + 300f/size + (600f/size + 5) * i));
            lines.getChildren().add(new Line(30 +300/size + (600f/size + 5) * i, 15, 30 + 300f/size + (600f/size + 5) * i, 640 + size * 5));
            for(int j = 0; j < size; j++) {
                Field field = new Field(i, j, 300f/size);
                stones.getChildren().add(field);
                board[i][j] = field;
            }
        }

        getChildren().addAll(rectangle, lines, stones);

        if(AppManager.getInstance().isMyTurn()) {
            color = StoneColor.BLACK;
            System.out.println("I'm black player");
        } else {
            color = StoneColor.WHITE;
            System.out.println("I'm white player");
        }
    }

    final class Field extends Circle {
        int i;
        int j;

        private void sendMove() {
            char[][] boardState = AppManager.getInstance().sendMove(i, j);
            if (boardState != null) {
                rearrange(boardState);
                AppManager.getInstance().waitForOpponentsMove();
            }
        }

        public Field(int i, int j, float r) {
            this.i = i;
            this.j = j;
            float gap = 2 * r + 5;
            setRadius(r);
            setCenterX(30 + r + i * gap);
            setCenterY(30 + r + j * gap);
            setFill(Color.TRANSPARENT);
            setOnMouseClicked(event -> sendMove());
        }
    }
}
