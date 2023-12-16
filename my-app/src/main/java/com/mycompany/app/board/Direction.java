package com.mycompany.app.board;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int x;
    private int y;

    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Direction valueOf(int ind){
        return Direction.values()[ind];
    }

    public static Direction reverse(Direction direction){
        return Direction.valueOf(direction.ordinal() ^ 1);
    }

    public Direction reverse(){
        return Direction.valueOf(this.ordinal() ^ 1);
    }

    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
