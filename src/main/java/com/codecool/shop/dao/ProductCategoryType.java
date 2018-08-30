package com.codecool.shop.dao;

public enum ProductCategoryType {
    TABLET(1, "tablet"),
    MOBILE(2, "mobile"),
    LAPTOP(3, "laptop");

    private int id;
    private String name;

    ProductCategoryType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ProductCategoryType getPCT(int id) {
        for (ProductCategoryType each: ProductCategoryType.values()) {
            if (each.id == id) {
                return each;
            }
        } return null;
    }

    public static ProductCategoryType getPCT(String name) {
        for (ProductCategoryType each: ProductCategoryType.values()) {
            if (name.equals(each.getName())) {
                return each;
            }
        } return null;
    }
}
