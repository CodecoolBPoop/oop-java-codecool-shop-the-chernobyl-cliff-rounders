package com.codecool.shop.model;

public class User extends BaseModel {

    private String email;
    private String password;

    public User(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
