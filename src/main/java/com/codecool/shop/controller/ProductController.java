package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.database.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.database.ProductDaoDB;
import com.codecool.shop.dao.implementation.database.SupplierDaoDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoDB.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoDB.getInstance();
        SupplierDao supplierDataStore = SupplierDaoDB.getInstance();
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        String productId = req.getParameter("product");
        if (productId != null) {
            Product productToAdd = productDataStore.find(Integer.parseInt(productId));
            shoppingCart.add(productToAdd);
        }

        String chosenCategory = req.getParameter("category");
        Map<String, Object> params = new HashMap<>();

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("allProductCategory", productCategoryDataStore.getAll());
        context.setVariable("suppliers", supplierDataStore.getAll());

        if (chosenCategory != null && !chosenCategory.equals("all")) {
            if (chosenCategory.matches("pc.*")) {
                chosenCategory = chosenCategory.split("-")[1];
                ProductCategory productCategory = productCategoryDataStore.getByName(chosenCategory);
                params.put("products", productDataStore.getBy(productCategory));
            } else if (chosenCategory.matches("s.*")) {
                chosenCategory = chosenCategory.split("-")[1];
                Supplier supplier = supplierDataStore.getByName(chosenCategory);
                params.put("products", productDataStore.getBy(supplier));
            }
            params.put("selected", chosenCategory);
            context.setVariables(params);
        } else {
            context.setVariable("selected", "all");
            context.setVariable("products", productDataStore.getAll());
        }

        context.setVariable("numberOfItems", shoppingCart.getSize());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("product/index.html", context, resp.getWriter());
    }
}
