package com.mycompany.app.board;

import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;
import com.mycompany.app.board.exceptions.OutOfBorderException;

//import java.util.ArrayList;
//import java.util.List;

public class Board {
    //List<StoneGroup> stoneGroups = new ArrayList<StoneGroup>();
    private int size;
    private Stone board[][];
    private char antiKo[][];
    private char antiKoBuffor[][];

    Board (int size){
        this.size = size;
        this.board = new Stone[size][size];
        antiKo = getSimplifiedBoard();
        antiKoBuffor = getSimplifiedBoard();
    }


    
    public void addStone(int x, int y, StoneColor color) throws IncorrectStonePlacementException{
        if(board[x][y] != null){
            throw new IncorrectStonePlacementException();
        }

        Stone stone = new Stone(x, y, color, this);
        System.out.println("new Stone");
        board[x][y] = stone;

        char[][] simple = getSimplifiedBoard();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (simple[i][j] != antiKo[i][j]) {
                    for (int k = 0; k < size; k++) {
                        for (int l = 0; l < size; l++) {
                            antiKo[k][l] = antiKoBuffor[k][l];
                            antiKoBuffor[k][l] = simple[k][l];
                        }
                    }
                    return;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                removeStone(i, j);
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (antiKoBuffor[i][j] == 'B') {
                    stone = new Stone(i, j, StoneColor.BLACK, this);
                    board[i][j] = stone;
                } else if (antiKoBuffor[i][j] == 'W') {
                    stone = new Stone(i, j, StoneColor.WHITE, this);
                    board[i][j] = stone;
                }
            }
            System.out.println();
        }
        for (int k = 0; k < size; k++) {
            for (int l = 0; l < size; l++) {
                antiKo[k][l] = antiKoBuffor[k][l];
                antiKoBuffor[k][l] = simple[k][l];
            }
        }
        throw new IncorrectStonePlacementException();
    }


    public Stone getStone(int x, int y) throws OutOfBorderException{
        if (x < 0 || y < 0 || x >= size || y >= size){
            throw new OutOfBorderException();
        }

        return board[x][y];
    }

    public Stone getStone(int x, int y, Direction direction) throws OutOfBorderException{
        x += direction.getX();
        y += direction.getY();

        return getStone(x, y);
    }


    public void removeStone(int x, int y){
        board[x][y] = (Stone)null;
    }


    public void printBoard(){
        String boardPrint = "";

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(j > 0) { boardPrint += ' '; }

                Stone stone = board[j][i];
                if(stone == null){ boardPrint += '+'; }
                else if(StoneColor.BLACK.equals(stone.getColor())){ boardPrint += 'B'; }
                else{ boardPrint += 'W'; }
            }

            boardPrint += "\n";
        }

        System.out.println(boardPrint);
    }

    public char[][] getSimplifiedBoard(){
        char[][] simplifiedBoard = new char[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Stone stone = board[j][i];

                if(stone == null){ simplifiedBoard[j][i] = 'E'; }
                else if(StoneColor.BLACK.equals(stone.getColor())){ simplifiedBoard[j][i] = 'B'; }
                else{ simplifiedBoard[j][i] = 'W'; }
            }
        }

        return simplifiedBoard;
    }

    @Override
    public String toString(){
        String boardString = "";

        for(int i = 0; i < size; i++){
            if(i > 0){ boardString += '\n'; }

            for(int j = 0; j < size; j++){
                Stone stone = board[j][i];

                if(stone == null){ boardString += ' ';}
                else if(StoneColor.BLACK.equals(stone.getColor())){ boardString += 'B'; }
                else{ boardString += 'W'; }

                boardString += ' ';
            }
        }

        return boardString;
    }

    public int getSize() {
        return size;
    }
}
