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


    void addStone(Stone stone){
        breaths += stone.getBreaths();
        stones.add(stone);
    }

    public void removeBreath(){
        breaths--;

        if(breaths == 0){ die(); }
    }
    
    public void addBreath(){
        breaths++;
    }

    public int getBreaths(){
        return breaths;

    }
    

    public int calcBreaths(){
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


    private void die(){
        if(calcBreaths() != breaths){ return; }

        for(Stone stone : stones){
            stone.die();
        }

        stones.clear();
    }

    public StoneColor getColor(){
        return color;
    }
}
