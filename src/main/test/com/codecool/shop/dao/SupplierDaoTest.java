package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

class SupplierDaoTest extends DaoTest {
    private Supplier testSupplier = new Supplier("Test supplier", "For testing.");

    SupplierDaoTest() {
        setStore(supplierDataStore);
        setInitialSize(2);
        setTestItem(testSupplier);
    }
}