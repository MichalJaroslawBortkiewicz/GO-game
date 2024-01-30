package com.mycompany.app.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class DataBaseManager implements IDataBaseManager{
    private final String dbURL;
    private final String login;
    private final String password;
    
    protected Connection connection;

    private final static String insertQuery = "INSERT INTO game_moves (game_id, move_nr, `move`) VALUES (?, ?, ?)";
    private final static String callQuery = "CALL new_game(?)";
    private final static String updateQuery = "UPDATE game_moves SET point_difference = ?, black_won = ? WHERE game_id = ? ";
    private final static String readQuery = "SELECT `move` FROM game_moves WHERE game_id = ? ORDER BY move_nr ASC";

    
    protected int gameNr;
    protected int moveNr = 1;


    DataBaseManager(String dbURL, String login, String password, int gameNr){
        this.dbURL = dbURL;
        this.login = login;
        this.password = password;
        
        connect();

        this.gameNr = gameNr;
    }

    DataBaseManager(String dbURL, String login, String password){
        this.dbURL = dbURL;
        this.login = login;
        this.password = password;
        
        connect();

        this.gameNr = createGame();
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
    }

    @Override
    public void connect(){
        try{
            connection = DriverManager.getConnection(dbURL, login, password);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }


    public int createGame(){
        try (CallableStatement callableStatement = connection.prepareCall(callQuery);){
            callableStatement.registerOutParameter("amogus", Types.INTEGER);
            callableStatement.execute();
            return callableStatement.getInt(1);    
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return -1;
    }

    @Override
    public void saveMove(String move){
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);){
            preparedStatement.setInt(1, gameNr);
            preparedStatement.setInt(2, moveNr);
            
            if(move == null){
                preparedStatement.setNull(3, Types.VARCHAR);
            }
            else{
                preparedStatement.setString(3, move);
            }

            preparedStatement.executeUpdate();
            moveNr++;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void saveGame(int pointDifference, boolean blackWon){
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){
            preparedStatement.setInt(1, pointDifference);
            preparedStatement.setBoolean(2, blackWon);
            preparedStatement.setInt(3, gameNr);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> readMoves(int moveNr){
        List<String> moves = new ArrayList<String>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(readQuery)){
            preparedStatement.setInt(1, gameNr);
            
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                for(int i = 1; i <= moveNr; i++){
                    if(!resultSet.next()){ break; }
                    moves.add(resultSet.getString(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return moves;
    }
}