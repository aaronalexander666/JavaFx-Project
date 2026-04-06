package com.cts.javafxdemo.util;

import com.cts.javafxdemo.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility bridge for DB access used by pro-grade feature classes.
 * This keeps new pro code isolated from older DatabaseConnection usage.
 */
public class DatabaseUtil {

    private DatabaseUtil() {
        // Utility class
    }

    @SuppressWarnings("deprecation")
    public static Connection getConnection() throws SQLException {
        return new DatabaseConnection().getConnection();
    }
}
