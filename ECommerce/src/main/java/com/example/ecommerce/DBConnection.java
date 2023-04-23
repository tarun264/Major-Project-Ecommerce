package com.example.ecommerce;
import java.sql.*;

public class DBConnection {
    //connector: database to connect: url of database from sql
    private  final String dbURL= "jdbc:mysql://localhost:3306/ecommerce";
    //username from datatbase
    private  final String userName="root";
    //password of mysql database
    private  final String password= "12345";


    private Statement getStatement(){
        try {
            Connection connection=  DriverManager.getConnection(dbURL,userName,password);
            return connection.createStatement();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // to execute query
    public ResultSet getQueryTable(String query){
        try{
            Statement statement =getStatement();
            return statement.executeQuery(query);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int updateDatabase(String query){
        try{
            Statement statement =getStatement();
            return statement.executeUpdate(query);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DBConnection conn = new DBConnection();
        ResultSet rs =  conn.getQueryTable("select * from customer");
        if(rs!=null){
            System.out.println("connection successfull");
        }
        else{
            System.out.println("connection failed");
        }
    }
}
