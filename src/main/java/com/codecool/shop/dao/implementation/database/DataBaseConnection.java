package com.codecool.shop.dao.implementation.database;

import java.sql.*;

public class DataBaseConnection {

    final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    final String DB_USER = "postgres";
    final String DB_PASSWORD = "postgres";

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }
}
