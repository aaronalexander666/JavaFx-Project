package com.cts.javafxdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Enhanced Database Connection Manager.
 * Optimized for high-frequency "Big Mapping" and session stability.
 */
public class DatabaseConnection {

    // Future is thick: Ensure the timezone and SSL settings match 2026 security standards
    private static final String URL = "jdbc:mysql://localhost:3306/javafxdemo" +
            "?useSSL=true" +
            "&allowPublicKeyRetrieval=true" +
            "&serverTimezone=UTC" +
            "&connectTimeout=5000"; // 5-second timeout for responsiveness

    private static final String USER = "root";
    private static final String PASSWORD = "A#$wwyou666";

    // Static initializer for Driver registration
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("CRITICAL: MySQL JDBC Driver missing from classpath!");
        }
    }

    /**
     * Provides a fresh, validated connection.
     * Use exclusively with try-with-resources in DAO classes.
     */
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            // Standard check for validity (1-second timeout check)
            if (conn.isValid(1)) {
                return conn;
            } else {
                throw new SQLException("Connection established but not valid.");
            }
        } catch (SQLException e) {
            // Log the specific error (e.g., Access Denied, DB not found)
            System.err.println("Database Access Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Static factory method to get a database connection without deprecation warnings.
     * Recommended for new code in DAO classes.
     */
    public static Connection openConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn.isValid(1)) {
                return conn;
            } else {
                throw new SQLException("Connection established but not valid.");
            }
        } catch (SQLException e) {
            System.err.println("Database Access Error: " + e.getMessage());
            throw e;
        }
    }
    /**
     * Helper to test the connection during application startup.
     */
    public static boolean testConnection() {
        try (Connection c = new DatabaseConnection().getConnection()) {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            System.err.println("Startup Connection Test Failed: " + e.getMessage());
            return false;
        }
    }

    // --- DEPRECATED LEGACY METHODS (Scheduled for Removal) ---

    @Deprecated
    public Connection con;

    @Deprecated
    public DatabaseConnection() {
        // Warning: Direct assignment leads to resource leaks. Use getConnection() instead.
    }
}