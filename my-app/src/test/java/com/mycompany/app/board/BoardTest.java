package com.mycompany.app.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {
    StoneColor black = StoneColor.BLACK;
    StoneColor white = StoneColor.WHITE;

    
    //TODO Sprawdzić czy oddechy się zgadzają po usuwaniu / postawieniu.
    @Test
    public void stoneRemovalTest1(){ //remove surrounded stone placed at the corner
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(0, 0, white);
            board.addStone(0, 1, black);

            assertEquals("  B   \nB     \n      ", board.toString());
        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }

    @Test
    public void stoneRemovalTest2(){ //remove surrounded stone placed at the corner
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(1, 2, black);            
            board.addStone(1, 1, white);
            board.addStone(0, 1, black);
            board.addStone(2, 1, black);

            assertEquals("  B   \nB   B \n  B   ", board.toString());
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
        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( false );
        }
    }

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
            assertTrue( true );
            //System.out.println("Źle ustawiony kamyk");
        }
    }

    @Test
    public void invalidStonePlacementTest2(){ //Single in the middle of others
        Board board = new Board(3);
        
        try{
            board.addStone(1, 0, black);
            board.addStone(1, 2, black);            
            board.addStone(0, 1, black);
            board.addStone(2, 1, black);
            board.addStone(1, 1, white);

            assertTrue( false );
        }
        catch(IncorrectStonePlacementException ex){
            assertTrue( true );
            //System.out.println("Źle ustawiony kamyk");
        }
    }
}


