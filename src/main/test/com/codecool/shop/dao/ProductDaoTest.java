package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDaoTest extends DaoTest<Product> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        setStore(productDataStore);
        setInitialSize(5);
        setTestItem(testProduct);
    }

    @Test
    void getBySupplierReturnsCorrectItems() {
        Supplier supplier = supplierDataStore.find(2);
        productDataStore.getBy(supplier).forEach(prod -> assertEquals(supplier, prod.getSupplier()));
    }

    @Test
    void getByCategoryReturnsCorrectItems() {
        ProductCategory category = productCategoryDataStore.find(2);
        productDataStore.getBy(category).forEach(prod -> assertEquals(category, prod.getProductCategory()));
    }
}