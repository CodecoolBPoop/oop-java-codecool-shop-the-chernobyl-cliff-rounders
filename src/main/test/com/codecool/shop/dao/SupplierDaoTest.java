package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;

class SupplierDaoTest extends DaoTest<Supplier> {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        setStore(supplierDataStore);
        setInitialSize(2);
        setTestItem(testSupplier);
    }
}