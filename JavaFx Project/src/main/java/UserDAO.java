package com.cts.javafxdemo;

import com.cts.javafxdemo.models.User;
import java.sql.*;

/**
 * Enhanced UserDAO - Data Access Layer.
 * Optimized for Session Mapping and resource integrity.
 */
public class UserDAO {

    private final DatabaseConnection dbConnection = new DatabaseConnection();

    /**
     * Authenticates and retrieves the full User index.
     * Returning a User object instead of a boolean enables 'thick' session management.
     */
    public User login(String username, String password, String userType) {
        String tableName = "customer".equalsIgnoreCase(userType) ? "tblcustomer" : "tblstaff";
        String idColumn = "customer".equalsIgnoreCase(userType) ? "customer_id" : "staff_id";

        String sql = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt(idColumn));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUserType(userType);

                    // Map optional fields if they exist in the table
                    if ("customer".equalsIgnoreCase(userType)) {
                        user.setEmail(rs.getString("email"));
                        user.setPhone(rs.getString("phone"));
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            handleError("Authentication", e);
        }
        return null; // Signals login failure
    }

    /**
     * Registers a new customer with conflict indexing check.
     */
    public boolean registerCustomer(String user, String pass, String name,
                                    String mail, String phone, String addr) {
        String sql = "INSERT INTO tblcustomer (username, password, full_name, email, phone, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setString(3, name);
            ps.setString(4, mail);
            ps.setString(5, phone);
            ps.setString(6, addr);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            handleError("Registration", e);
            return false;
        }
    }

    /**
     * Checks if username is already indexed in the chosen role table.
     */
    public boolean usernameExists(String username, String userType) {
        String tableName = "customer".equalsIgnoreCase(userType) ? "tblcustomer" : "tblstaff";
        String sql = "SELECT 1 FROM " + tableName + " WHERE username = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            handleError("Exist Check", e);
            return false;
        }
    }

    private void handleError(String context, SQLException e) {
        System.err.println("UserDAO " + context + " Error: " + e.getMessage());
    }
    public boolean authenticate(String username, String password, String userType) {
        User user = login(username, password, userType);
        return user != null;
    }

    public User getUserByUsername(String username, String userType) {
        return null;
    }
}
