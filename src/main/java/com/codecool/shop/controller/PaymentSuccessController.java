package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
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

@WebServlet(urlPatterns = {"/success"})
public class PaymentSuccessController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if (shoppingCart.getSize() == 0 ){
            resp.sendRedirect("/");
        }

        String name = req.getParameter("cardname");
        String total = req.getParameter("amount");
        context.setVariable("name",name);
        context.setVariable("total",total);


        engine.process("paymentSuccess.html", context, resp.getWriter());
        shoppingCart.clear();
    }

}