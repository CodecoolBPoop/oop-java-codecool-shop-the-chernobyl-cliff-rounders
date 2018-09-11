package com.codecool.shop.dao;

import com.codecool.shop.model.BaseModel;

import java.util.List;

public interface Dao<T extends BaseModel> {
    void add(T type);
    T find(int id);
    void remove(int id);
    void clear();

    List<T> getAll();
}
