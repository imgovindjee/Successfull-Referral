package com.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection(){
        try {
//            ESTABLISHING THE DRIVER CLASS
            Class.forName("com.mysql.cj.jdbc.Driver");

//            CREATING THE CONNECTION
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/simplifymoneyassignment", "root", "$Govindjee123_");
//            return connection;
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return connection;
    }
}
