package com.codecool.shop.model;

import com.codecool.shop.dao.ProductCategoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends BaseModel {
    private static final Logger logger = LoggerFactory.getLogger(ProductCategory.class);
    private String department;
    private List<Product> products;
    private ProductCategoryType type;


    public ProductCategory(int categoryId, String name, String department, String description) {
        super(name, description);
        this.department = department;
        type = ProductCategoryType.getPCT(categoryId);
        setId(categoryId);
        this.products = new ArrayList<>();
        logger.info("ProductCategory has been added");

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}