package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.database.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.database.ProductDaoDB;
import com.codecool.shop.dao.implementation.database.SupplierDaoDB;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class DaoTest<T extends BaseModel> {
    private Dao<T> store;
    private int initialSize;
    private T testItem;

    ProductDao productDataStore = ProductDaoDB.getInstance();
    ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
    SupplierDao supplierDataStore = SupplierDaoDB.getInstance();

    Product testProduct;
    ProductCategory testCategory;
    Supplier testSupplier;

    public void setStore(Dao<T> store) {
        this.store = store;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public void setTestItem(T testItem) {
        this.testItem = testItem;
    }

    @BeforeEach
    void setUp() {

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory(
                ProductCategoryType.TABLET.getId(),"Tablet", "Hardware",
                "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory laptop = new ProductCategory(
                ProductCategoryType.LAPTOP.getId(),"Laptop", "Hardware",
                "These are portable laptops.");
        productCategoryDataStore.add(laptop);
        ProductCategory mobile = new ProductCategory(
                ProductCategoryType.MOBILE.getId(),"Mobile", "Hardware",
                "These are mobile phones.");
        productCategoryDataStore.add(mobile);

        //setting up products and printing it
        productDataStore.add(new Product(
                ProductCategoryType.TABLET.getId(),"Lenovo IdeaPad Miix 700", 479, "USD",
                "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.",
                tablet, lenovo));
        productDataStore.add(new Product(
                ProductCategoryType.TABLET.getId(),"Amazon Fire HD 8", 89, "USD",
                "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product(
                ProductCategoryType.LAPTOP.getId(),"Apple Macbook Pro", 5000, "USD",
                "Very high price. Best portable laptop ever.", laptop, amazon));
        productDataStore.add(new Product(
                ProductCategoryType.MOBILE.getId(),"Apple Iphone", 500, "USD",
                "Fantastic price. The best phone ever.", mobile, amazon));
        productDataStore.add(new Product(
                ProductCategoryType.LAPTOP.getId(),"Lenovo ThinkPad X1 Extreme", 2079, "USD",
                "Extreme power. Extreme portability. Extreme possibilities.", laptop, lenovo));

        testProduct = new Product(1, "Test product", 3.5F, "USD",
                "Test product.", laptop, amazon);
        testCategory = new ProductCategory(1, "Test category",
                "Test department", "Test description.");
        testSupplier = new Supplier("Test supplier", "For testing.");
    }

    @AfterEach
    void tearDown() {
        productDataStore.clear();
        productCategoryDataStore.clear();
        supplierDataStore.clear();
    }

    @Test
    void getAllReturnsCorrectSize() {
        assertEquals(initialSize, store.getAll().size());
    }

    @Test
    void clearEmptiesTheStore() {
        store.clear();
        assertEquals(0, store.getAll().size());
    }

    @Test
    void newProductReceivesUniqueIdAfterRemoval() {
        store.remove(initialSize - 1);
        store.add(testItem);
        assertEquals(testItem, store.find(testItem.getId()));
    }

    @Test
    void findReturnsNullWithNonExistentId() {
        assertNull(store.find(initialSize + 1));
    }

    @Test
    void removeDoesNothingWhenProvidedWithInvalidId() {
        store.remove(initialSize + 1);
        assertEquals(initialSize, store.getAll().size());
    }
}
