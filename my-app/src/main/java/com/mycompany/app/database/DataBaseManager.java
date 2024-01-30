package com.mycompany.app.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DataBaseManager implements IDataBase{
    private final String dbURL;
    private final String login;
    private final String password;
    private int gameNr;

    private Connection connection;
    
    private int moveNr = 1;
    private final static String insertQuery = "INSERT INTO game_moves (game_id, move_nr, `move`) VALUES (?, ?, ?)";
    private final static  String callQuery = "{? = CALL new_game()}";
    private final static String updateQuery = "UPDATE game_moves SET point_difference = ?, black_won = ? WHERE game_id = ? ";


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
            callableStatement.registerOutParameter(1, Types.INTEGER);
            return callableStatement.getInt(1);    
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return -1;
    }

    
    /*
    @Override
    public void createDBSchema(){
        try{
            Statement statement = connection.createStatement();    
            StringBuilder sqlQuery = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))){
                String line;
                while ((line = br.readLine()) != null) {
                    sqlQuery.append(line).append("\n");
                }
            } catch (IOException ex){}

            statement.executeUpdate(sqlQuery.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    */
    

    @Override
    public void saveMoves(String move){
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);){
            preparedStatement.setInt(1, gameNr);
            preparedStatement.setInt(2, moveNr);

            if(move == null){
                preparedStatement.setNull(3, Types.ARRAY);
            }

            preparedStatement.setString(3, move);
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
    
}
