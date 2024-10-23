package com.ilnaz.server.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfiguration {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, username, password);
    }
}
