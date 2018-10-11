package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.database.ProductDaoDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ShoppingCart;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

@WebServlet(urlPatterns = {"/reviewCart", "/updateCart"})
public class ShoppingCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ShoppingCart shoppingCart = ShoppingCart.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        List<Product> cart = shoppingCart.getAll();

        double total = cart.stream().mapToDouble(Product::getDefaultPrice).sum();
        String currency = cart.stream().map(Product::getDefaultCurrency)
                                        .findFirst()
                                        .orElse(Currency.getInstance("USD"))
                                        .toString();

        Map<Product, Long> products = cart.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Object> contextVariables = new HashMap<>();

        contextVariables.put("currency", currency);
        contextVariables.put("shoppingCartItems", products);
        contextVariables.put("numberOfItems", cart.size());
        contextVariables.put("total", total);

        context.setVariables(contextVariables);

        engine.process("reviewCart.html", context, resp.getWriter());

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        ProductDao productDataStore = ProductDaoDB.getInstance();

        String request = req.getReader().lines().collect(Collectors.joining());

        JsonObject json = (JsonObject) new JsonParser().parse(request);
        JsonElement element = json.getAsJsonArray("action");

        String[] action = new Gson().fromJson(element, String[].class);

        int productId = Integer.parseInt(action[0]);

        if (action[1].equals("add")) {
            shoppingCart.add(productDataStore.find(productId));
        } else {
            shoppingCart.remove(productId);
        }

    }
}
