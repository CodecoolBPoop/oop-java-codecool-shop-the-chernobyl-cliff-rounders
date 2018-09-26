package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoDb extends DataBaseConnection implements UserDao {

    private static UserDaoDb instance = null;

    private UserDaoDb() {
    }

    public static UserDaoDb getInstance() {
        if (instance == null) instance = new UserDaoDb();
        return instance;
    }


    @Override
    public void add(User type) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users(name, email, password) VALUES (?,?,?) RETURNING id;")) {
            statement.setString(1, type.getName());
            statement.setString(2, type.getEmail());
            statement.setString(3, type.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) type.setId(resultSet.getInt("id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT name, email FROM users WHERE id=?;")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                user.setId(id);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM users WHERE id=?;")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        String query = "TRUNCATE TABLE user RESTART IDENTITY;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

