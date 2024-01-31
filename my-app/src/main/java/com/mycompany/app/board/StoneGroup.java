package com.mycompany.app.board;

import java.util.ArrayList;
import java.util.List;

public class StoneGroup {
    private List<Stone> stones = new ArrayList<Stone>();
    private StoneColor color;
    private int breaths;


    StoneGroup(Stone stone){
        breaths = stone.getBreaths();
        color = stone.getColor();
        stones.add(stone);
    }


    
    public void addStone(Stone stone){
        breaths += stone.getBreaths();
        stones.add(stone);
    }
    
    public void addStones(List<Stone> newStones){
        stones.addAll(newStones);
    }

    public void extend(StoneGroup otherStoneGroup){
        if(this == otherStoneGroup){ return; }
        breaths += otherStoneGroup.getBreaths();
        stones.addAll(otherStoneGroup.dieAlone(this));
    }


    public List<Stone> dieAlone(StoneGroup newStoneGroup){
        for(Stone stone : stones){
            stone.setStoneGroup(newStoneGroup);            
        }

        return stones;
    }

    private void dieAll(){
        if(calcBreaths() != breaths){ return; }

        GameManager instance = GameManager.getInstance();

        if(StoneColor.BLACK.equals(color)){
            instance.addWhitePoints(stones.size());
        }
        else{
            instance.addBlackPoints(stones.size());
        }

        for(Stone stone : stones){
            stone.die();
        }

        stones.clear();
    }

    
    private int calcBreaths(){
        int tempBreaths = 0;
        for(Stone stone : stones){
            tempBreaths += stone.getBreaths();
        }

        return tempBreaths;
    }

    public boolean resetBreaths(){
        int newBreaths = calcBreaths();
        
        if(breaths == newBreaths){ return false; }
        breaths = newBreaths;
        return true;
    }
    
    public void removeBreath(){
        breaths--;
        if(breaths == 0){ dieAll(); }
    }
    
    public void addBreath(){
        breaths++;
    }

    public int getBreaths(){
        return breaths;
    }
    

    public StoneColor getColor(){
        return color;
    }

    public List<Stone> getStones(){
        return stones;
    }
}
