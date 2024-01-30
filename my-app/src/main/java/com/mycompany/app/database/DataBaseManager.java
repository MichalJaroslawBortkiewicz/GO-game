package com.mycompany.app.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    final String dbURL;
    final String login;
    final String password;
    final String sqlFilePath = "./go_schema.sql";


    DataBaseManager(String dbURL, String login, String password){
        this.dbURL = dbURL;
        this.login = login;
        this.password = password;

        createDBSchema();
    }


    private void createDBSchema(){
        try(Connection connection = DriverManager.getConnection(dbURL, login, password);
            Statement statement = connection.createStatement();    
        ){
            StringBuilder sqlQuery = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))){
                String line;
                while ((line = br.readLine()) != null) {
                    sqlQuery.append(line).append("\n");
                }
            } catch (IOException ex){}

            statement.executeUpdate(sqlQuery.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
}
