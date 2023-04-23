package com.example.ecommerce;

public class Customer {
    private int id;
    private String name,email,mobile;

    //create constructor using rightclick generate


    public Customer(int id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
    // get getter setters using right click


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
