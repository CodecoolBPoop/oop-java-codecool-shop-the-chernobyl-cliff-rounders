package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB extends DataBaseConnection implements ProductCategoryDao {

    private static ProductCategoryDaoDB instance = null;

    private ProductCategoryDaoDB() {
    }

    public static ProductCategoryDaoDB getInstance() {
        if (instance == null) instance = new ProductCategoryDaoDB();
        return instance;
    }

    @Override
    public ProductCategory getByName(String name) {
        ProductCategory category = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, name, department, description FROM product_category WHERE name=?;")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                category = new ProductCategory(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("department"),
                    resultSet.getString("description"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void add(ProductCategory type) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO product_category(name, department, description) VALUES (?,?,?)" +
                        "RETURNING id;")) {
            statement.setString(1, type.getName());
            statement.setString(2, type.getDepartment());
            statement.setString(3, type.getDescription());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) type.setId(resultSet.getInt("id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory category = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT name, department, description FROM product_category WHERE id=?;")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category = new ProductCategory(
                        id,
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                category.setId(id);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM product_category WHERE id=?;")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        String query = "DROP TABLE IF EXISTS product_category; CREATE TABLE product_category (" +
                "id serial  NOT NULL, name varchar(255)  NOT NULL, department varchar(255)  NOT NULL," +
                "description varchar(255)  NOT NULL, CONSTRAINT product_category_pk PRIMARY KEY (id));";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM product_category";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                ProductCategory category = new ProductCategory(
                        categoryId,
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                category.setId(categoryId);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
