package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static boolean placeOrder(Customer customer, Product product){
        String groupOrderId= "SELECT max(group_order_id)+1 id from orders";
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            // if rs.next is true then we will place the order
            if(rs.next()){
                String placeOrder = "INSERT INTO orders(group_order_id,customer_id,product_id) VALUES ("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDatabase(placeOrder)!=0;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
// for multiple order selected
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        String groupOrderId= "SELECT max(group_order_id)+1 id from orders";
        DBConnection dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.getQueryTable(groupOrderId);
            int count=0;
            // if rs.next is true then we will place the order
            if(rs.next()){
                for (Product product: productList){
                    String placeOrder = "INSERT INTO orders(group_order_id,customer_id,product_id) VALUES ("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count+= dbConnection.updateDatabase(placeOrder);
                }
                return count;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
