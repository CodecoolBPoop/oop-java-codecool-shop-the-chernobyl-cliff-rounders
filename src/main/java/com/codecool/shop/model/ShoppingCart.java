package com.codecool.shop.model;


import com.codecool.shop.dao.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart implements ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCart.class);

    private List<Product> data = new ArrayList<>();
    private static ShoppingCart instance = null;

    private ShoppingCart() {
    }

    public static ShoppingCart getInstance() {
        if (instance ==null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void add(Product product) {
        data.add(product);
        logger.info("Product added: [{}] {}", product.getId(), product.getName());
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        Product product = find(id);
        data.remove(product);
        logger.info("Product removed: [{}] {}", product.getId(), product.getName());
    }

    public int getSize() {
        return data.size();
    }

    public List<Product> getAll() {
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }


    public int getActualItemQuantity(int id) {
        int quantity = 0;
        for(Product product : data) {
            if(product.id == id) {
                quantity += 1;
            }
        }
        return quantity;
    }
}
