package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.memory.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDB extends DataBaseConnection implements SupplierDao {

    private static SupplierDaoDB instance = null;

    private SupplierDaoDB() { }

    public static SupplierDaoDB getInstance() {
        if (instance == null) instance = new SupplierDaoDB();
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO supplier(name, description)" +
                            "VALUES (?,?);")) {
                statement.setString(1, supplier.getName());
                statement.setString(2, supplier.getDescription());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT name, description FROM supplier WHERE id=?;")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            supplier = new Supplier(
                    resultSet.getString("name"),
                    resultSet.getString("description"));
                resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM supplier WHERE id=?;")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        String query = "DROP TABLE IF EXISTS supplier;" +
                "CREATE TABLE supplier (id serial  NOT NULL, name varchar(255)  NOT NULL, description varchar(255)  NOT NULL,\n" +
                "CONSTRAINT supplier_pk PRIMARY KEY (id));";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM supplier";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
}

