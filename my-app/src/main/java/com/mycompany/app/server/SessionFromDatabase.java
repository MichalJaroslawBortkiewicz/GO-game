package com.mycompany.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.mycompany.app.board.GameManager;
import com.mycompany.app.board.exceptions.IncorrectStonePlacementException;
import com.mycompany.app.board.exceptions.NotYourTurnException;
import com.mycompany.app.database.IDataBaseManager;
import com.mycompany.app.database.WrongGameNumberException;

public class SessionFromDatabase implements Session {
    private IDataBaseManager dataBaseManager;
    private Socket player;
    private int size;
    
    private DataInputStream input;
    private DataOutputStream output;
    
    private boolean sesionRun = true;
    
    private int prevMoveNr = -1;
    private char[][] boardState = new char[size][size];

    public SessionFromDatabase(Socket player, int size, IDataBaseManager dataBaseManager)
    {
        this.player = player;
        this.size = size;
        this.dataBaseManager = dataBaseManager;

        try{
            input = new DataInputStream(player.getInputStream());
            output = new DataOutputStream(player.getOutputStream());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }


    
    private void getMove() throws Exception{
        int moveNr = input.readInt();
            
        if(prevMoveNr == moveNr){
            output.writeBoolean(false);

            boardState = GameManager.getInstance().getSimplifiedBoard();
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    output.writeChar(boardState[i][j]);
                }
            }
        
            return;
        }


        List<String> moves = dataBaseManager.readMoves(moveNr);


        int counter = 0;
        
        GameManager.getInstance().startGame(size);

        boolean black = false;
        for(String move : moves){
            black = !black;
            if("PASS".equals(move) || "FF".equals(move) || "NULL".equals(move)){
                return; 
            }
            counter++;

            String[] splitMove = move.split(":");
            int x = Integer.parseInt(splitMove[0]);
            int y = Integer.parseInt(splitMove[1]);

            GameManager.getInstance().addStone(x, y, black ? 0 : 1);
        }
        
        if(counter != moveNr){
            output.writeBoolean(true);
            return;
        }
        output.writeBoolean(false);
        
        
        boardState = GameManager.getInstance().getSimplifiedBoard();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                output.writeChar(boardState[i][j]);
            }
        }
        
        prevMoveNr = moveNr;
    }

    private void changeGame() throws Exception{
        int gameNr = dataBaseManager.getGameNr();
        System.out.println(gameNr);
        if(input.readBoolean()){
            gameNr++;
        }
        else{
            gameNr--;
        }
        System.out.println(gameNr);
        try{
            dataBaseManager.setGameNr(gameNr);
        }
        catch(WrongGameNumberException ex){
            ex.printStackTrace();
        }
        System.out.println(size);
        this.size = dataBaseManager.getSize();
        System.out.println(size);
        output.writeInt(size);
    }

    @Override
    public void run() {
        startGame();

        try{
            while(sesionRun){
                if(input.readBoolean()){
                    getMove();
                }
                else{
                    changeGame();
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void startGame() {
        GameManager.getInstance().startGame(size);
    }

    @Override
    public void addStone(int x, int y, int player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addStone'");
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }
    

    @Override
    public void setProposition(char[][] proposition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProposition'");
    }
}
