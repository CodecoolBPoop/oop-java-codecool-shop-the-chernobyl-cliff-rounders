package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.memory.ProductDaoMem;
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

@WebServlet(urlPatterns = {"/reviewCart"})
public class ShoppingCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        ShoppingCart shoppingCart = ShoppingCart.getInstance();


        double total = 0;

        for (Product product : shoppingCart.getAll()) {
            total+=product.getDefaultPrice();
        }

        String id = req.getParameter("id");
        if (id != null) {
            int quantity = shoppingCart.getActualItemQuantity(Integer.parseInt(req.getParameter("id")));
        }

        String currency = "";
        for (Product product: shoppingCart.getAll()) {
            currency+=product.getDefaultCurrency();
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("currency",currency);
        context.setVariable("numberOfItems",shoppingCart.getSize());
        context.setVariable("shoppingCartItems", shoppingCart.getAll());


        if (req.getParameterNames().hasMoreElements()) {
            if (req.getParameter("method").equals("remove")) {
                for (Product product : shoppingCart.getAll()) {
                    if (product.getId() == Integer.parseInt(id)){
                        total -= product.getDefaultPrice();
                        break;
                    }
                }
                shoppingCart.remove(Integer.parseInt(req.getParameter("id")));
            }
        }

        context.setVariable("total", total);
        engine.process("reviewCart.html", context, resp.getWriter());

    }
}
