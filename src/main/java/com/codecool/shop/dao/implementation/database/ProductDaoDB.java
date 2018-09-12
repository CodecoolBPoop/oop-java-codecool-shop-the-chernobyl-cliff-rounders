package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryType;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

public class ProductDaoDB extends DataBaseConnection implements ProductDao {

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement();
            ) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Product product = new Product(
                            resultSet.getInt("product_category_id"),
                            resultSet.getString("name"),
                            resultSet.getFloat("default_price"),
                            resultSet.getString("currency"),
                            resultSet.getString("description"),
                            new ProductCategory(resultSet.getInt("product_category_id"),"Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display."),
                            new Supplier("Amazon", "Digital content and services"));
                            products.add(product);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
