package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB extends DataBaseConnection implements ProductDao {

    private static ProductDaoDB instance = null;

    private ProductDaoDB() { }

    public static ProductDaoDB getInstance() {
        if (instance == null) instance = new ProductDaoDB();
        return instance;
    }

    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
    private SupplierDao supplierDataStore = SupplierDaoDB.getInstance();

    @Override
    public void add(Product product)
    {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO product(name, default_price, currency, description, product_category_id, supplier_id)" +
                        "VALUES (?,?,?,?,?,?) RETURNING id;")) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getDefaultPrice());
            statement.setObject(3, product.getDefaultCurrency(), Types.VARCHAR);
            statement.setString(4, product.getDescription());
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6, product.getSupplier().getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) product.setId(resultSet.getInt("id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM product WHERE id=?;")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int categoryId = resultSet.getInt("product_category_id");
                product = new Product(
                        categoryId,
                        resultSet.getString("name"),
                        resultSet.getFloat("default_price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDataStore.find(categoryId),
                        supplierDataStore.find(resultSet.getInt("supplier_id")));
                product.setId(resultSet.getInt("id"));
            }
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
        String query = "DROP TABLE IF EXISTS product; CREATE TABLE product (" +
                "id serial  NOT NULL, name varchar(255)  NOT NULL, default_price numeric  NOT NULL," +
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
             ResultSet resultSet = statement.executeQuery(query)) {
            addProducts(products, resultSet);
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
            addProducts(products, resultSet);
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
            addProducts(products, resultSet);
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private void addProducts(List<Product> products, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int categoryId = resultSet.getInt("product_category_id");
            Product product = new Product(
                    categoryId,
                    resultSet.getString("name"),
                    resultSet.getFloat("default_price"),
                    resultSet.getString("currency"),
                    resultSet.getString("description"),
                    productCategoryDataStore.find(categoryId),
                    supplierDataStore.find(resultSet.getInt("supplier_id")));
            product.setId(resultSet.getInt("id"));
            products.add(product);
        }
    }
}
