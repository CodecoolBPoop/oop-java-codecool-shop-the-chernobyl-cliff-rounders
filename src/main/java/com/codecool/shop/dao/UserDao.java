package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao extends Dao<User> {
    User getByName(String name);
}


