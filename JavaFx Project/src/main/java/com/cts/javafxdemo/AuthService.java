package com.cts.javafxdemo;

import com.cts.javafxdemo.models.User; // Assuming a User model exists

/**
 * Enhanced Authentication Service - Business Logic Layer.
 * Manages user sessions and credential mapping.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    // Session State
    private static String currentUsername;
    private static String currentUserType;
    private static int currentUserId;
    private static String currentFullName;

    /**
     * Validates user credentials and indexes the session.
     */
    public boolean login(String username, String password, String userType) {
        // Business logic: prevent empty attempts before hitting the DAO
        if (username == null || password == null || username.isEmpty()) {
            return false;
        }

        boolean isValid = userDAO.authenticate(username, password, userType);

        if (isValid) {
            // Mapping the session data for global access
            currentUsername = username;
            currentUserType = userType;

            // Future is thick: Fetch the full user index to populate session details
            User user = userDAO.getUserByUsername(username, userType);
            if (user != null) {
                currentUserId = user.getUserId();
                currentFullName = user.getFullName();
            }
        }

        return isValid;
    }

    /**
     * Registers a new customer with validation mapping.
     */
    public boolean registerCustomer(String username, String password, String fullName,
                                    String email, String phone, String address) {
        // Business validation mapping
        if (isInvalidInput(username) || isInvalidInput(password) || password.length() < 4) {
            return false;
        }

        // Prevent duplicate indexing in the User table
        if (userDAO.usernameExists(username, "Customer")) {
            return false;
        }

        return userDAO.registerCustomer(username, password, fullName, email, phone, address);
    }

    private boolean isInvalidInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    // --- Session Accessors ---

    public static String getCurrentUsername() { return currentUsername; }
    public static String getCurrentUserType() { return currentUserType; }
    public static int getCurrentUserId() { return currentUserId; }
    public static String getCurrentFullName() { return currentFullName; }

    public static boolean isLoggedIn() {
        return currentUsername != null;
    }

    /**
     * Clears current session and wipes indexed user data.
     */
    public static void logout() {
        currentUsername = null;
        currentUserType = null;
        currentUserId = 0;
        currentFullName = null;
        System.out.println("Session cleared. User logged out.");
    }
}