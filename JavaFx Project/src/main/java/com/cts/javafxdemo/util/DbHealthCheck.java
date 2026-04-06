package com.cts.javafxdemo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Pre-flight database connectivity checker.
 * Run before launching the main JavaFX application to ensure clean error reporting.
 */
public class DbHealthCheck {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/javafxdemo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "A#$wwyou666";
    private static final int TIMEOUT_SECONDS = 5;

    /**
     * Test if database is reachable and valid.
     * @return true if connection succeeds, false otherwise
     */
    public static boolean isDatabaseReachable() {
        System.out.println("🔍 Checking Database Connection...");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                if (conn != null && conn.isValid(TIMEOUT_SECONDS)) {
                    System.out.println("✅ Database connection verified: " + DB_URL);
                    return true;
                }
                return false;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC driver not found: " + e.getMessage());
            System.err.println("   Ensure mysql-connector-j is in pom.xml dependencies");
            return false;
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            System.err.println("\n   Troubleshooting:");
            System.err.println("   • Is MySQL running? (net start MySQL80 on Windows)");
            System.err.println("   • Check credentials in DbHealthCheck.java");
            System.err.println("   • Verify port 3306 is not blocked by firewall");
            return false;
        }
    }

    /**
     * Run pre-flight checks before app launch.
     * @return 0 if checks pass, 1 if database fails
     */
    public static int runPreFlight() {
        System.out.println("\n========================================");
        System.out.println("🔍 PRE-FLIGHT SYSTEM CHECK");
        System.out.println("========================================");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("JavaFX Available: " + isJavaFxAvailable());
        
        boolean dbOk = isDatabaseReachable();
        
        if (!dbOk) {
            System.err.println("\n⚠️ WARNING: Database connection failed.");
            System.err.println("   The application may launch in limited/offline mode.");
            return 1;
        }
        
        System.out.println("\n✅ All pre-flight checks passed. Launching application...");
        System.out.println("========================================\n");
        return 0;
    }

    /**
     * Check if JavaFX is available in the classpath.
     */
    private static boolean isJavaFxAvailable() {
        try {
            Class.forName("javafx.application.Application");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Standalone entry point for testing database connectivity independently.
     */
    public static void main(String[] args) {
        int exitCode = runPreFlight();
        System.exit(exitCode);
    }
}
