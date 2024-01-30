package com.mycompany.app.board;

import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;
import com.mycompany.app.board.exceptions.OutOfBorderException;

//import java.lang.reflect.Array;
//import java.util.ArrayList;

public class Stone {
    private StoneGroup stoneGroup;
    private StoneColor color;


    private Board board;
    /*
     private Stone up;
     private Stone down;
     private Stone left;
     private Stone right;
     */
     
    Stone neighbors[] = new Stone[4]; //= {up, down, left, right};

    private int breaths = 4;
    private int x;
    private int y;

    Stone(int x, int y, StoneColor color, Board board) throws IncorrectStonePlacementException{
        this.x = x;
        this.y = y;
        this.color = color;

        this.board = board;

        placeStone();
    }

    Stone(int x, int y, StoneColor color) throws IncorrectStonePlacementException{
        this.x = x;
        this.y = y;
        this.color = color;

        placeStone();
    }

    private void placeStone() throws IncorrectStonePlacementException{
        Boolean allyDie = false;
        Boolean enemyDie = false;
        Boolean hasAllies = false;

        System.out.println("zero");
        
        for(Direction direction : Direction.values()){
            Stone stone;
            try{
                stone = board.getStone(x, y, direction);
            }
            catch(OutOfBorderException ex){
                breaths--;
                continue;
            }

            neighbors[direction.ordinal()] = stone;

            if(stone == null){ continue; }

            breaths--;
            if(stone.getColor().equals(color)){ hasAllies = true; }
        }

        System.out.println("one");

        stoneGroup = new StoneGroup(this);

        if(breaths > 0){
            for(int i = 0; i < 4; i++){
                Stone stone = neighbors[i];
                if(stone == null){ continue; }

                stone.addNeighbor(this, Direction.valueOf(i ^ 1));
                if(!stone.getColor().equals(color)){ continue; }

                stoneGroup.extend(stone.getStoneGroup());
            }

            System.out.println("two");

            return;
        }

        for(Stone stone : neighbors){
            if(stone == null){ continue; }
            StoneGroup newGroup = stone.getStoneGroup();
            newGroup.removeBreath();
            int groupBreaths = newGroup.getBreaths();

            if(groupBreaths > 0){ continue; }

            if(newGroup.getColor().equals(color)){ allyDie = true; }
            else{ enemyDie = true; }
        }

        System.out.println("three");

        if( (allyDie || !hasAllies) && !enemyDie ){
            for(Stone stone : neighbors){
                if(stone == null){ continue; }
                stone.getStoneGroup().addBreath();
                stone = (Stone)null;
            }
            throw new IncorrectStonePlacementException();
        }

        System.out.println("three and a half");

        for(int i = 0; i < 4; i++){
            Stone stone = neighbors[i];
            if(stone == null || stone.getColor().equals(color)){ continue; }

            stone.getStoneGroup().addBreath();
            stone.addNeighbor(this, Direction.valueOf(i ^ 1));
        }

        System.out.println("four");

        for(int i = 0; i < 4; i++){
            Stone stone = neighbors[i];
            if(stone == null || !stone.getColor().equals(color)){ continue; }

            StoneGroup otherStoneGroup = stone.getStoneGroup();
            otherStoneGroup.addBreath();
            stone.addNeighbor(stone, Direction.valueOf(i ^ 1));

            stoneGroup.extend(otherStoneGroup);
        }
        
        System.out.println("five");
    }
    
    
    public void addNeighbor(Stone stone, Direction direction){
        neighbors[direction.ordinal()] = stone;
        breaths--;
        stoneGroup.removeBreath();
    }
    
    public void removeNeighbor(Direction direction){
        neighbors[direction.ordinal()] = (Stone)null;
        breaths++;
        if(stoneGroup != null){ stoneGroup.addBreath(); }
    }

    public void die(){
        for(int i = 0; i < 4; i++){
            Stone stone = neighbors[i];
            if(stone == null){ continue; }

            stone.removeNeighbor(Direction.valueOf(i ^ 1));
            neighbors[i] = (Stone)null;
        }

        board.removeStone(x, y);
        stoneGroup = (StoneGroup)null;
    }

    public int getBreaths(){
        return breaths;
    }

    public StoneGroup getStoneGroup() {
        return stoneGroup;
    }

    public void setStoneGroup(StoneGroup stoneGroup){
        this.stoneGroup = stoneGroup;
    }

    public StoneColor getColor(){
        return color;
    }
}