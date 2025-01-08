package edu.itu.mat335e.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url =   "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/data/inventory.db";

    public static Connection connection() throws SQLException {
        try {
            return (DriverManager.getConnection(url));
        }
        catch (SQLException e) {
            throw new SQLException("Connection failed: " + e.getMessage());
        }
    }
}
