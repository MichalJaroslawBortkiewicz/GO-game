package com.mycompany.app.board;

public class Stone {
    private StoneGroup stoneGroup;

    private Stone up;
    private Stone right;
    private Stone down;
    private Stone left;

    private int breaths = 4;
    private int x;
    private int y;

    Stone(int x, int y){
        this.x = x;
        this.y = y;

    }

    public StoneGroup getStoneGroup() {
        return stoneGroup;
    }

    public void addNeighbor(Stone stone, Direction direction){
        
    }

}
