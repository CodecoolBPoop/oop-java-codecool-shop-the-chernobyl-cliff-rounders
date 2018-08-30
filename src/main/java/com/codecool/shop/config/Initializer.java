package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductCategoryType;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory(ProductCategoryType.TABLET.getId(),"Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        System.out.println(tablet.getId());
        productCategoryDataStore.add(tablet);
        ProductCategory laptop = new ProductCategory(ProductCategoryType.LAPTOP.getId(),"Laptop", "Hardware", "These are portable laptops.");
        System.out.println(laptop.getId());
        productCategoryDataStore.add(laptop);
        ProductCategory mobile = new ProductCategory(ProductCategoryType.MOBILE.getId(),"Mobile", "Hardware", "These are mobile phones.");
        System.out.println(mobile.getId());
        productCategoryDataStore.add(mobile);

        //setting up products and printing it
        productDataStore.add(new Product(ProductCategoryType.TABLET.getId(),"Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product(ProductCategoryType.TABLET.getId(),"Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product(ProductCategoryType.MOBILE.getId(),"Apple Iphone", 500.0f, "USD", "Fantastic price.The best phone ever.", mobile, amazon));
        productDataStore.add(new Product(ProductCategoryType.LAPTOP.getId(),"Apple Macbook Pro", 5000.0f, "USD", "Very high price. Best portable laptop ever.", laptop, amazon));
        productDataStore.add(new Product(ProductCategoryType.LAPTOP.getId(),"Apple Macbook Pro", 5000.0f, "USD", "Very high price. Best portable laptop ever.", laptop, amazon));    }
}
