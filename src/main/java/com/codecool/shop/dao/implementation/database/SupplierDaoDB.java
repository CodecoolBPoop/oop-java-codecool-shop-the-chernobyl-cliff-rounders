package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.SupplierDao;
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
                    "INSERT INTO supplier(name, description) VALUES (?,?) RETURNING id;")) {
                statement.setString(1, supplier.getName());
                statement.setString(2, supplier.getDescription());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) supplier.setId(resultSet.getInt("id"));
                resultSet.close();
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
            if (resultSet.next()) {
                supplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(id);
            }
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
        String query = "TRUNCATE TABLE supplier RESTART IDENTITY;";
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
                supplier.setId(resultSet.getInt("id"));
                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
}

