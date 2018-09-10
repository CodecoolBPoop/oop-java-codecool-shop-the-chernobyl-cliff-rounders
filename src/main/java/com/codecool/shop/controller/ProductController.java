package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductCategoryType;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        String productId = req.getParameter("product");
        if (productId != null) {
            Product productToAdd = productDataStore.find(Integer.parseInt(productId));
            shoppingCart.add(productToAdd);
        }

        String chosenCategory = req.getParameter("category");
        Map params = new HashMap<>();

        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("allProductCategory", productCategoryDataStore.getAll());

        if (chosenCategory != null) {
            ProductCategory productCategory = productCategoryDataStore.getByName(chosenCategory);
            params.put("products", productDataStore.getBy(productCategory));
            context.setVariables(params);
        } else {
            context.setVariable("products", productDataStore.getAll());
        }

        context.setVariable("numberOfItems", shoppingCart.getSize());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("product/index.html", context, resp.getWriter());
    }
}
