package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao extends Dao<ProductCategory> {

    ProductCategory getByName(String name);

}
