package com.mathwhiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = setting("mathwhiz.db.url", "MATHWHIZ_DB_URL", DEFAULT_URL);
        String user = setting("mathwhiz.db.user", "MATHWHIZ_DB_USER", "root");
        String password = setting("mathwhiz.db.password", "MATHWHIZ_DB_PASSWORD", "");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("The MySQL JDBC driver is not available.", exception);
        }
        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String property, String environmentVariable, String defaultValue) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }
        return value == null ? defaultValue : value;
    }
}
