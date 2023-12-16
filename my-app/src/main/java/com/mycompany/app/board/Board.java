package com.mycompany.app.board;

//import java.util.ArrayList;
//import java.util.List;

public class Board {
    //List<StoneGroup> stoneGroups = new ArrayList<StoneGroup>();
    private int size;
    private Stone board[][];

    Board (int size){
        this.size = size;
        this.board = new Stone[size][size];
    }

    
    public void addStone(int x, int y, StoneColor color) throws IncorrectStonePlacementException{
        Stone stone = new Stone(x, y, color);
        board[y][x] = stone;
    }


    public Stone getStone(int x, int y, Direction direction) throws OutOfBorderException{
        x += direction.getX();
        y += direction.getY();

        if (x < 0 || y < 0 || x >= size || y >= size){
            throw new OutOfBorderException();
        }

        return board[y][x];
    }


    public void removeStone(int x, int y){
        board[y][x] = (Stone)null;
    }


    public void printBoard(){
        System.out.println("");
    }
}
