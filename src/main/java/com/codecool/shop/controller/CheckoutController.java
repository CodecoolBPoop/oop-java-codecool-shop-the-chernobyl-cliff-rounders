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
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<Product> cart = shoppingCart.getAll();

        if (cart.isEmpty()) {
            resp.sendRedirect("/");
            return;
        }

        double total = cart.stream().mapToDouble(Product::getDefaultPrice).sum();
        String currency = cart.stream().map(Product::getDefaultCurrency)
                                       .findFirst()
                                       .orElse(Currency.getInstance("USD"))
                                       .toString();

        Map<String, Object> contextVariables = new HashMap<>();

        Map<Product, Long> products = cart.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        contextVariables.put("currency", currency);
        contextVariables.put("shoppingCartItems", products);
        contextVariables.put("numberOfItems", cart.size());
        contextVariables.put("total", total);

        context.setVariables(contextVariables);
        engine.process("checkout.html", context, resp.getWriter());
    }

}
