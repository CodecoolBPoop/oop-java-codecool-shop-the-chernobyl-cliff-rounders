package com.codecool.shop.model;


import com.codecool.shop.utilities.Bcrypt;

public class User extends BaseModel {

    private String email;
    private String password;

    public User(String name, String email, String password) {
        super(name);
        this.email = email;
        this.password = Bcrypt.hashPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
