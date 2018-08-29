package com.codecool.shop.model;


import com.codecool.shop.dao.ProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart implements ProductDao {

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
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
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

}
