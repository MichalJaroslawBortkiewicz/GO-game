package com.mycompany.app.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.List;

import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;

public class StonePlacementTest extends StoneTestHelper{
    @Test
    public void stonePlacementTest1(){
        try{
            Board board = new Board(5);

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(i != 0 && i != 4 && j != 0 && j != 4){ continue; }
                    board.addStone(i, j, black);
                }    
            }

            List<Stone> stones = getStonesFromBoard(board);
            board.printBoard();

            assertEquals(16, stones.get(0).getStoneGroup().getStones().size());
        }   
        catch(IncorrectStonePlacementException ex){
            assertTrue(false);
        }
    }
}