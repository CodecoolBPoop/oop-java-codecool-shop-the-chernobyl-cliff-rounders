package com.codecool.shop.model;


import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

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

    public int getSize() {
        return data.size();
    }

    public List<Product> getAll() {
        return data;
    }


}
