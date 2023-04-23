package com.example.ecommerce;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    //with its help we can view the table
    private TableView<Product> productTable;

    public VBox createTable(ObservableList<Product> data){

        //column
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

//table creation
        productTable= new TableView<>();
//adding column name
        productTable.getColumns().addAll(id,name,price);
//adding value to the column
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox= new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(productTable);
        return vbox;
    }

    public VBox getAllProducts(){
        // it will fetch all the products
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }
    // it will get the value of the selected item in product table
    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }
// creating table for items in cart
    public VBox getProductsInCart(ObservableList<Product> data){
        return createTable(data);
    }
}
