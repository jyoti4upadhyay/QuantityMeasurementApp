package com.measurementApp.units;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {

    public static Connection getConnection() {

        Connection conn=null;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quantityDB",
                    "root",
                    "jyoti@2004");

            System.out.println("Database Connected");

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return conn;
    }
}