package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCategoryDaoTest extends DaoTest<ProductCategory> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        setStore(productCategoryDataStore);
        setInitialSize(3);
        setTestItem(testCategory);
    }

    @Test
    void getByNameReturnsCorrectCategory() {
        assertEquals(2, productCategoryDataStore.getByName("Laptop").getId());
    }
}