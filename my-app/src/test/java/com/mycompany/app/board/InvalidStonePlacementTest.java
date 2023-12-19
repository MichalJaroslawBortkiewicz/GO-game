package com.mycompany.app.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;

public class InvalidStonePlacementTest extends StoneTestHelper{

    @Test
    public void invalidStonePlacementTest1(){ //Single, at corner
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(0, 1, black);
            board.addStone(0, 0, white);

            assertTrue( false );
        }
        catch(IncorrectStonePlacementException ex){
            List<Stone> stones = getStonesFromBoard(board);

            assertEquals(2, stones.size());

            for(Stone stone : stones){
                assertEquals(3, stone.getBreaths());
                assertEquals(3, stone.getStoneGroup().getBreaths());
                assertEquals(1, stone.getStoneGroup().getStones().size());
            }
        }
    }

    @Test
    public void invalidStonePlacementTest2(){ //Single in the middle of others
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(0, 1, black);
            board.addStone(2, 1, black);
            board.addStone(1, 2, black);
            board.addStone(1, 1, white);

            assertTrue( false );
        }
        catch(IncorrectStonePlacementException ex){
            List<Stone> stones = getStonesFromBoard(board);

            assertEquals(4, stones.size());


            for(Stone stone : stones){
                assertEquals(3, stone.getBreaths());
                assertEquals(3, stone.getStoneGroup().getBreaths());
                assertEquals(1, stone.getStoneGroup().getStones().size());
            }
        }
    }

    @Test
    public void invalidStonePlacementTest3(){ //Single in the middle of others
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(2, 0, black);
            board.addStone(0, 1, black);
            board.addStone(1, 1, white);
            board.addStone(1, 2, black);
            board.addStone(2, 2, black);

            board.addStone(2, 1, white);

            assertTrue( false );
        }
        catch(IncorrectStonePlacementException ex){
            List<Stone> stones = getStonesFromBoard(board);
            int breaths[] = {1, 1, 2, 1, 1, 1};
            int groupBreaths[] = {2, 2, 2, 1, 2, 2};
            int groupSize[] = {2, 2, 1, 1, 2, 2};

            assertEquals(6, stones.size());
            
            for(int i = 0; i < 6; i++){
                Stone stone = stones.get(i);
                assertEquals(breaths[i], stone.getBreaths());
                assertEquals(groupBreaths[i], stone.getStoneGroup().getBreaths());
                assertEquals(groupSize[i], stone.getStoneGroup().getStones().size());
            }

        }
    }

    @Test
    public void invalidStonePlacementTest4(){ //Single in the middle of others
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(2, 0, black);
            board.addStone(0, 1, black);
            board.addStone(2, 1, white);
            board.addStone(1, 2, black);
            board.addStone(2, 2, black);
            
            board.addStone(1, 1, white);
            
            assertTrue( false );
        }
        catch(IncorrectStonePlacementException ex){
            List<Stone> stones = getStonesFromBoard(board);
            int breaths[] = {2, 0, 3, 1, 2, 0};
            int groupBreaths[] = {2, 2, 3, 1, 2, 2};
            int groupSize[] = {2, 2, 1, 1, 2, 2};

            assertEquals(6, stones.size());
            
            for(int i = 0; i < 6; i++){
                Stone stone = stones.get(i);
                assertEquals(breaths[i], stone.getBreaths());
                assertEquals(groupBreaths[i], stone.getStoneGroup().getBreaths());
                assertEquals(groupSize[i], stone.getStoneGroup().getStones().size());
            }

        }
    }

    @Test
    public void invalidStonePlacementTest5(){
        Board board = new Board(3);

        try {
            board.addStone(0, 0, black);
            board.addStone(0, 0, white);    
            assertTrue(false);
        }
        catch (IncorrectStonePlacementException ex){
            
        }
    }
}
