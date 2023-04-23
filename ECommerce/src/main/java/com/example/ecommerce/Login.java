package com.example.ecommerce;

import java.sql.ResultSet;


// this class will help to recognise the user provided info during login is matching
// with our database or not
public class Login {
    public Customer customerLogin(String userName, String password) {
        String loginQuery = "SELECT * FROM customer WHERE email ='" + userName + "' AND password = '" + password + "'";
        DBConnection conn = new DBConnection();
        ResultSet rs = conn.getQueryTable(loginQuery);
        try {
            if (rs.next()) {
                return new Customer(rs.getInt("id"),rs.getString("name"),
                        rs.getString("email"),rs.getString("mobile"));
            }
        }
        catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        public static void main (String[]args){
            Login login = new Login();
            Customer customer= login.customerLogin("tarun@gmail.com", "abc123");
            System.out.println("Welcome: "+customer.getName());
        }
    }


