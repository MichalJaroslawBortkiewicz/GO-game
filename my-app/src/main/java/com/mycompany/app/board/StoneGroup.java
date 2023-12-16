package com.mycompany.app.board;

import java.util.ArrayList;
import java.util.List;

public class StoneGroup {
    private List<Stone> stones = new ArrayList<Stone>();
    private int breaths;




    void addStone(Stone stone){
        stones.add(stone);
    }
}
