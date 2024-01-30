package com.mycompany.app.database;

public interface IDataBase {
    void connect();
    void saveMoves(String move);
    void saveGame(int pointDifference, boolean blackWon);
}
