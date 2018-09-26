package com.codecool.shop.dao.implementation.memory;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements UserDao {

    private List<User> userData = new ArrayList<>();
    private static UserDaoMem instance = null;
    private static int nextId = 1;

    private UserDaoMem() {

    }

    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }

    @Override
    public void add(User user) {
        user.setId(nextId++);
        userData.add(user);
    }

    @Override
    public User find(int id) {
        return userData.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        userData.remove(find(id));
    }

    @Override
    public void clear() {
        userData = new ArrayList<>();
        nextId = 1;
    }

    @Override
    public List<User> getAll() {
        return userData;
    }

    @Override
    public User getByName(String name) {
        return null;
    }
}
