package com.mycompany.app.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;

public class StoneRemovalTest extends StoneTestHelper{    
    @Test
    public void stoneRemovalTest1(){ //remove surrounded stone placed at the corner
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(0, 0, white);
            board.addStone(0, 1, black);

            assertEquals("  B   \nB     \n      ", board.toString());

            List<Stone> stones = getStonesFromBoard(board);
            assertEquals(2, stones.size());

            for(Stone stone : stones){
                assertEquals(3, stone.getBreaths());
                assertEquals(3, stone.getStoneGroup().getBreaths());
                assertEquals(1, stone.getStoneGroup().getStones().size());
            }
        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }

    @Test
    public void stoneRemovalTest2(){ //remove surrounded stone placed between stones
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(0, 1, black);
            board.addStone(1, 1, white);
            board.addStone(2, 1, black);
            board.addStone(1, 2, black);

            assertEquals("  B   \nB   B \n  B   ", board.toString());

            List<Stone> stones = getStonesFromBoard(board);

            assertEquals(4, stones.size());

            for(Stone stone : stones){
                assertEquals(3, stone.getBreaths());
                assertEquals(3, stone.getStoneGroup().getBreaths());
                assertEquals(1, stone.getStoneGroup().getStones().size());
            }
        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }

    @Test
    public void stoneRemovalTest3(){
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(2, 0, black);
            board.addStone(0, 1, black);
            board.addStone(1, 1, white);
            board.addStone(2, 1, white);
            board.addStone(1, 2, black);

            board.addStone(2, 2, black);

            assertEquals("  B B \nB     \n  B B ", board.toString());

            List<Stone> stones = getStonesFromBoard(board);

            assertEquals(5, stones.size());

            int breaths[] = {2, 1, 3, 2, 1};
            int groupSizes[] = {2, 2, 1, 2, 2};

            for(int i = 0; i < 5; i++){
                Stone stone = stones.get(i);
                System.out.println(i);
                assertEquals(breaths[i], stone.getBreaths());
                assertEquals(3, stone.getStoneGroup().getBreaths());
                assertEquals(groupSizes[i], stone.getStoneGroup().getStones().size());
            }

        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }


    @Test
    public void stoneRemovalTest4(){
        Board board = new Board(4);

        try{
            board.addStone(1, 0, white);
            board.addStone(2, 0, black);
            board.addStone(0, 1, white);
            board.addStone(2, 1, white);
            board.addStone(3, 1, black);
            board.addStone(1, 2, white);
            board.addStone(2, 2, black);

            board.addStone(1, 1, black);


            assertEquals("  W B   \nW B   B \n  W B   \n        ", board.toString());
            
            List<Stone> stones = getStonesFromBoard(board);

            assertEquals(7, stones.size());

            int breaths[] = {1, 2, 2, 1, 3, 2, 3};
            int groupBreaths[] = breaths;

            for(int i = 0; i < 7; i++){
                Stone stone = stones.get(i);
                System.out.println(i);
                assertEquals(breaths[i], stone.getBreaths());
                assertEquals(groupBreaths[i], stone.getStoneGroup().getBreaths());
                assertEquals(1, stone.getStoneGroup().getStones().size());
            }
        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }

    @Test
    public void stoneRemovalTest5(){
        Board board = new Board(3);

        try{
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(i == 1 && j == 1 || i == 2 && j == 2){ continue; }
                    board.addStone(i, j, white);
                }
            }
            board.printBoard();

            board.addStone(2, 2, white);
            board.addStone(1, 1, black);


            assertEquals("      \n  B   \n      ", board.toString());

            List<Stone> stones = getStonesFromBoard(board);
    
            assertEquals(1, stones.size());

            Stone stone = stones.get(0);
            assertEquals(4, stone.getBreaths());
            assertEquals(4, stone.getStoneGroup().getBreaths());
            assertEquals(1, stone.getStoneGroup().getStones().size());

        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }

}