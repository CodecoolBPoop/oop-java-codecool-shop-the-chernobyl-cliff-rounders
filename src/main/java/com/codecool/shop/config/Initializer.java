package com.codecool.shop.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /*
        ProductDao productDataStore = ProductDaoDB.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
        SupplierDao supplierDataStore = SupplierDaoDB.getInstance();

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
        productDataStore.add(new Product(ProductCategoryType.LAPTOP.getId(),"Apple Macbook Pro", 5000, "USD", "Very high price. Best portable laptop ever.", laptop, amazon));
        productDataStore.add(new Product(ProductCategoryType.MOBILE.getId(),"Apple Iphone", 500, "USD", "Fantastic price.The best phone ever.", mobile, amazon));
        productDataStore.add(new Product(
                ProductCategoryType.LAPTOP.getId(),"Lenovo ThinkPad X1 Extreme", 2079, "USD",
                "Extreme power. Extreme portability. Extreme possibilities.", laptop, lenovo));

*/

    }

}
