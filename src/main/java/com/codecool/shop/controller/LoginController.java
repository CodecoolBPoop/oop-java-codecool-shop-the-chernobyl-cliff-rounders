package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.database.UserDaoDb;
import com.codecool.shop.model.User;
import com.codecool.shop.utilities.Bcrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao users = UserDaoDb.getInstance();

        User user = users.getByName(req.getParameter("username"));

        if (user != null && Bcrypt.verifyPassword(req.getParameter("password"), user.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        }

        resp.sendRedirect("/");

    }
}
