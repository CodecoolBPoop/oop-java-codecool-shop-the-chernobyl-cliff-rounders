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

@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        if (shoppingCart.getSize() == 0 ){
            resp.sendRedirect("/");
        }

        double total = 0;

        for (Product product : shoppingCart.getAll()) {
            total += product.getDefaultPrice();
        }

        String currency = "";
        for (Product product : shoppingCart.getAll()) {
            currency += product.getDefaultCurrency();
            break;
        }
        String amount = req.getParameter("amount");
        if (amount != null) {
            if (Integer.parseInt(amount)==total) {
                System.out.println("Successfull payment");
            } else {
                System.out.println("not successfull payment");
            }
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("currency",currency);
        context.setVariable("total", total);

        engine.process("payment.html", context, resp.getWriter());
    }

}