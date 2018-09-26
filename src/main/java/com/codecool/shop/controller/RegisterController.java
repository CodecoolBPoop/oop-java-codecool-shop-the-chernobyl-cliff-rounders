package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.database.UserDaoDb;
import com.codecool.shop.model.User;
import com.codecool.shop.utilities.RegistrationForm;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;


@WebServlet(urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        context.setVariable("errorMessages", new String[0]);
        engine.process("register.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        RegistrationForm form = new RegistrationForm();
        form.setUsername(req.getParameter("username"));
        form.setEmail(req.getParameter("email"));
        form.setPassword(req.getParameter("password"));
        form.setPassword2(req.getParameter("password2"));

        Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form);

        if (violations.isEmpty()) {
            UserDaoDb.getInstance().add(new User(form.getUsername(), form.getEmail(), form.getPassword()));
            resp.sendRedirect("/");
        } else {
            String[] errorMessages = violations.stream().map(ConstraintViolation::getMessage).toArray(String[]::new);
            context.setVariable("errorMessages", errorMessages);
            engine.process("register.html", context, resp.getWriter());
        }
    }
}