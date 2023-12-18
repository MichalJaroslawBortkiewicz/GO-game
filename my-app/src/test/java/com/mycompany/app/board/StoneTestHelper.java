package com.mycompany.app.board;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class StoneTestHelper {
    StoneColor black = StoneColor.BLACK;
    StoneColor white = StoneColor.WHITE;
    
    public List<Stone> getStonesFromBoard(Board board){
        List<Stone> stones = new ArrayList<Stone>();
        int size = board.getSize();

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                try{
                    Stone stone = board.getStone(j, i);
                    if(stone != null){
                        stones.add(stone);
                    }
                }
                catch(Exception ex){
                    assertTrue(false);
                }
            }
        }
        return stones;
    }
}