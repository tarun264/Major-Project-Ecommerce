package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button signInButton;
    Label welcomeLabel;
    Customer loggedInCustomer;
    //object of productlist
    ProductList productList = new ProductList();
    VBox productPage;
    VBox body;
    Button placeOrderButton = new Button("Place Order");
    ObservableList<Product> itemInCart = FXCollections.observableArrayList();

    public BorderPane createContent() {
        BorderPane root = new BorderPane();
        // setting the pane size
        root.setPrefSize(800, 600);
        //adding Headerbar

        // method to add nodes as childern to the pane
        // root.getChildren().add(loginPage);
//        root.setCenter(loginPage);
        root.setTop(headerBar);
        // for body
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        body.setStyle("-fx-background-color: beige");
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
        //adding footer bar
        root.setBottom(footerBar);
        return root;
    }

    // calling the createlogin method from userInterface class
    public UserInterface() {
        createloginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createloginPage() {
        //text for username & pass
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");
// field to enter text
        TextField userName = new TextField();
        //it will prompt in user text filed
        userName.setPromptText("Type your uesrname here");

// password field to protect the passwrd
        PasswordField password = new PasswordField();
        //it will prompt in user passwordfield filed
        password.setPromptText("Type your password here");

        Label messageLabel = new Label("Hi! ");
        Button loginButton = new Button("Login");

        // inserting above text field to grid pane
        loginPage = new GridPane();
        // to set background color
        loginPage.setStyle("-fx-background-color: beige");
        // to set allignement
        loginPage.setAlignment(Pos.CENTER);

        // to set gap between login page and password
        loginPage.setHgap(10);
        loginPage.setVgap(10);

        // method to add child,colmn indx, rowIndx in gridpane
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(messageLabel, 0, 2);
        loginPage.add(loginButton, 1, 2);

        //to make button functional
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                loggedInCustomer = login.customerLogin(name, pass);
                // if the login is succesfull
                if (loggedInCustomer != null) {
                    messageLabel.setText("Welcome" + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    // remove everything from children
                    body.getChildren().clear();
                    // and display the product page
                    body.getChildren().add(productPage);
                } else {
                    messageLabel.setText("Login Failed! Please try again.");
                }
            }
        });

    }

    private void createHeaderBar() {
        //homepage button
        Button homebutton = new Button();
        Image image= new Image("C:\\Users\\Tarun\\IdeaProjects\\ECommerce\\src\\download (1).png");
        //to show the image in ui
        ImageView imageView =new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(20);
        homebutton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Here");
        searchBar.setPrefWidth(180);

        Button searchButton = new Button("Search");

        // button for signin
        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        //button for cart
        Button cartbutton = new Button("Cart");

        Button orderButton = new Button("Order");

        // Hbox is used for headerbar
        headerBar = new HBox(20);
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.setBackground(Background.fill(Color.LIGHTBLUE));
        headerBar.getChildren().addAll(homebutton,searchBar, searchButton, signInButton,cartbutton,orderButton);
// on clicking of signin button
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // remove everything from body
                body.getChildren().clear();
                //display login page
                body.getChildren().add(loginPage);
                // after click on sign in button removing it
                headerBar.getChildren().remove(signInButton);
            }
        });
        cartbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                //footbar will disappear when cart button is pressed
                footerBar.setVisible(false); // all cases need to handled for this
            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // need list of product and a customer

                if (itemInCart == null) {
                    showDialog("Please add some products in a cart to place order");
                    return;

                }//if the customer is not logged in
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer,itemInCart);
                if(count!=0){
                    showDialog("Order for "+count+"products placed successfully");
                }else{
                    showDialog("Order failed!");
                }
            }
        });
        // making home button functional
        homebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               // clear the body
                body.getChildren().clear();
                //show the homepage
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                //adding back signin again since it was vanishing
                if(loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton) == -1) {
                        headerBar.getChildren().add(signInButton);
                }
            }
        });

    }



    private void createFooterBar() {

        Button buyNowButton = new Button("Buy Now");

        //button for add to cart
        Button addToCart = new Button("Add to Cart");

        // Hbox is used for footerbar
        footerBar = new HBox(20);
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        headerBar.setBackground(Background.fill(Color.BEIGE));
        footerBar.getChildren().addAll(buyNowButton,addToCart);

        // enabling the buy now button to function
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //get the product that is selected in table
                Product product = productList.getSelectedProduct();

                // if the customer not selected anything, ask them to select
                if (product == null) {
                    showDialog("Please select a product first to place order");
                    return;

                }//if the customer is not logged in
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer,product);
                if(status==true){
                    showDialog("Order placed successfully");
                }else{
                    showDialog("Order failed!");
                }
            }
        });
        // to add items in cart
        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                // if the customer not selected anything, ask them to select
                if (product == null) {
                    showDialog("Please select a product first to add to cart");
                    return;
                }
                else{
                    itemInCart.add(product);
                    showDialog("Selected item is added to cart!");
                }

            }
        });
    }
// alert when the customer didnt select anything
    private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        // alert will be visible till you havent pressed anything
        alert.showAndWait();
    }

}
