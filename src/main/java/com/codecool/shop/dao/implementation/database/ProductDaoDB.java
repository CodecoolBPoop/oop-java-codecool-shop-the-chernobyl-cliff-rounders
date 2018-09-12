package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.memory.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends DataBaseConnection implements ProductDao {

    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

    @Override
    public void add(Product product)
    {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO product(name, default_price, currency, description, product_category_id, supplier_id)" +
                        "VALUES (?,?,?,?,?,?);");) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getDefaultPrice());
            statement.setObject(3, product.getDefaultCurrency(), Types.VARCHAR);
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6, product.getSupplier().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT name, default_price, currency, description, product_category_id, supplier_id FROM product" +
                             "WHERE id=?;")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getFloat("default_price"),
                    resultSet.getString("currency"),
                    resultSet.getString("description"),
                    productCategoryDataStore.find(resultSet.getInt("product_category_id")),
                    supplierDataStore.find(resultSet.getInt("supplier_id")));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void remove(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM product WHERE id=?;")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        String query = "DROP TABLE IF EXISTS product;" +
                "CREATE TABLE product (id serial  NOT NULL, name varchar(255)  NOT NULL, default_price numeric  NOT NULL," +
                "currency varchar(255)  NOT NULL, description varchar(255)  NOT NULL, product_category_id int  NOT NULL," +
                "supplier_id int  NOT NULL, CONSTRAINT product_pk PRIMARY KEY (id));";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDataStore.find(resultSet.getInt("product_category_id")),
                        supplierDataStore.find(resultSet.getInt("supplier_id")));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE supplier_id=?;")
        ) {
            statement.setInt(1, supplier.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDataStore.find(resultSet.getInt("product_category_id")),
                        supplierDataStore.find(resultSet.getInt("supplier_id")));
                products.add(product);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE product_category_id=?;")
        ) {
            statement.setInt(1, productCategory.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDataStore.find(resultSet.getInt("product_category_id")),
                        supplierDataStore.find(resultSet.getInt("supplier_id")));
                products.add(product);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


}
