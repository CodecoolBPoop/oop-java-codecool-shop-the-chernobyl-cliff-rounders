package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

public interface SupplierDao extends Dao<Supplier> {

    Supplier getByName(String name);

}
