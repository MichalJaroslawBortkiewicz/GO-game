package com.mycompany.app.database;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class DataBaseSaveTest {
    private String dbURL = "jdbc:mysql://localhost:3306/go_games";
    
    @Test
    public void dBSaveTest(){
        DataBaseManager dataBaseManager = new DataBaseManager(dbURL, "root", "password");


        dataBaseManager.saveMove("1:0");
        dataBaseManager.saveMove("0:0");
        dataBaseManager.saveMove("0:1");

        assertTrue(true);
    }

    
    @Test
    public void dbReadTest(){
        DataBaseManager dataBaseManager = new DataBaseManager(dbURL, "root", "password", 1);
    
        List<String> moves = dataBaseManager.readMoves(2);

        System.out.println(moves);
        assertTrue(true);
    }
}
