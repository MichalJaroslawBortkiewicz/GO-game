package com.mycompany.app.board;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Stone {
    private StoneGroup stoneGroup;
    private StoneColor color;

    private Stone up;
    private Stone down;
    private Stone left;
    private Stone right;
    
    Stone neighbors[] = {up, down, left, right};

    private int breaths = 4;
    private int x;
    private int y;

    Stone(int x, int y, StoneColor color) throws IncorrectStonePlacementException{
        this.x = x;
        this.y = y;
        this.color = color;

        placeStone();
    }

    private void placeStone() throws IncorrectStonePlacementException{
        ArrayList<StoneGroup> allyStoneGroups = new ArrayList<StoneGroup>();
        ArrayList<StoneGroup> enemyStoneGroups = new ArrayList<StoneGroup>();
        Board board = GameManager.getInstance().getBoard();
        Stone tempNeighbors[] = new Stone[4];
        
        int noNeighbors = 0;
        for(Direction direction : Direction.values()){
            Stone stone = board.getStone(x, y, direction);

            //tempNeighbors[direction.ordinal()] = stone;

            if(stone == null){ continue; }

            noNeighbors++;
            if(stone.getColor().equals(color)){
                allyStoneGroups.add(stone.getStoneGroup());
            }
            else{
                enemyStoneGroups.add(stone.getStoneGroup());
            }
        }


        if(noNeighbors != 4){
            for(int i = 0; i < 4; i++){
                neighbors = tempNeighbors;
                Stone stone = neighbors[i];
                stone.addNeighbor(this, Direction.valueOf(i ^ 1));
            }

        }
    }
    
    
    public void addNeighbor(Stone stone, Direction direction){
        neighbors[direction.ordinal()] = stone;
        breaths--;
    }
    
    public void removeNeighbor(Direction direction){
        neighbors[direction.ordinal()] = (Stone)null;
        breaths++;
    }

    public void die(){
        for(int i = 0; i < 4; i++){
            Stone stone = neighbors[i];
            if(stone == null){ continue; }

            stone.removeNeighbor(Direction.valueOf(i ^ 1));
        }
    }

    public StoneGroup getStoneGroup() {
        return stoneGroup;
    }

    public StoneColor getColor(){
        return color;
    }
}