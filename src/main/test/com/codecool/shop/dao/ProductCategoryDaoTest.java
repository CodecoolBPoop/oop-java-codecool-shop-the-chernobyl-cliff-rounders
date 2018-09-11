package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductCategoryDaoTest extends DaoTest {
    ProductCategory testCategory = new ProductCategory(1, "Test category",
            "Test department", "Test description.");

    ProductCategoryDaoTest() {
        setStore(productCategoryDataStore);
        setInitialSize(3);
        setTestItem(testCategory);
    }

    @Test
    void getByNameReturnsCorrectCategory() {
        assertEquals(2, productCategoryDataStore.getByName("Laptop").getId());
    }
}