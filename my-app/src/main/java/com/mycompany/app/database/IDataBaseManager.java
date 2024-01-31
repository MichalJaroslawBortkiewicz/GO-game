package com.mycompany.app.database;

import java.util.List;

public interface IDataBaseManager{
    void connect();
    void saveGame(int boardSize, int pointDifference, boolean blackWon);
    void saveMove(String move);
    List<String> readMoves(int moveNr);
    int getGameNr();
    void setGameNr(int gameNr) throws WrongGameNumberException;
    int getSize();
}
