package com.mycompany.app.board;

//import java.lang.reflect.Array;
//import java.util.ArrayList;

public class Stone {
    private StoneGroup stoneGroup;
    private StoneColor color;

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

    Stone(int x, int y, StoneColor color) throws IncorrectStonePlacementException{
        this.x = x;
        this.y = y;
        this.color = color;

        placeStone();
    }

    private void placeStone() throws IncorrectStonePlacementException{
        //ArrayList<StoneGroup> allyStoneGroups = new ArrayList<StoneGroup>();
        //ArrayList<StoneGroup> enemyStoneGroups = new ArrayList<StoneGroup>();
        Board board = GameManager.getInstance().getBoard();
        Stone tempNeighbors[] = new Stone[4];

        Boolean allyDie = false;
        Boolean enemyDie = false;
        
        for(Direction direction : Direction.values()){
            Stone stone;
            try{
                stone = board.getStone(x, y, direction);
            }
            catch(OutOfBorderException ex){
                breaths--;
                continue;
            }

            tempNeighbors[direction.ordinal()] = stone;

            if(stone == null){ continue; }

            breaths--;
            /*
            if(stone.getColor().equals(color)){
                allyStoneGroups.add(stone.getStoneGroup());
            }
            else{
                enemyStoneGroups.add(stone.getStoneGroup());
            }
            */
        }


        if(breaths > 0){
            neighbors = tempNeighbors;

            for(int i = 0; i < 4; i++){
                Stone stone = neighbors[i];
                if(stone == null){ continue; }

                stone.addNeighbor(this, Direction.valueOf(i ^ 1));
            }

            return;
        }

        for(Stone stone : tempNeighbors){
            if(stone == null){ continue; }
            StoneGroup newGroup = stone.getStoneGroup();
            newGroup.removeBreath();
            int groupBreaths = newGroup.getBreaths();

            if(groupBreaths > 0){ continue; }

            if(newGroup.getColor().equals(color)){
                allyDie = true;
            }
            else{
                enemyDie = true;
            }
        }

        if(allyDie && !enemyDie){
            for(Stone stone : neighbors){
                if(stone == null){ continue; }
                stone.getStoneGroup().addBreath();
            }
            throw new IncorrectStonePlacementException();
        }

        for(int i = 0; i < 4; i++){
            Stone stone = neighbors[i];
            if(stone == null){ continue; }
            stone.getStoneGroup().addBreath();
            stone.addNeighbor(stone, Direction.valueOf(i ^ 1));
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
            neighbors[i] = (Stone)null;
        }

        stoneGroup = (StoneGroup)null;
    }

    public int getBreaths(){
        return breaths;
    }

    public StoneGroup getStoneGroup() {
        return stoneGroup;
    }

    public StoneColor getColor(){
        return color;
    }
}