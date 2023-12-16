package com.mycompany.app.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    List<StoneGroup> stoneGroups = new ArrayList<StoneGroup>();
    private int size;










    public Stone getStone(int x, int y, Direction direction) throws OutOfBorderException{
        x += direction.getX();
        y += direction.getY();

        if (x < 0 || y < 0 || x >= size || y >= size){
            throw new OutOfBorderException();
        }

        return board[y][x];
    }
}
