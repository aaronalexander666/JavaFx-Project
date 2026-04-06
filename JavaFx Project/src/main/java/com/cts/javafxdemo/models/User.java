package com.cts.javafxdemo.models;

import javafx.beans.property.*;

/**
 * User Model - Represents a session-active user.
 * Implements JavaFX Properties for seamless UI data-binding.
 */
public class User {
    // UI-Observable Properties
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty fullName = new SimpleStringProperty();
    private final StringProperty userType = new SimpleStringProperty(); // "Customer" or "Staff"
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();

    // Default Constructor
    public User() {}

    /**
     * Overloaded Constructor for quick mapping from UserDAO.
     */
    public User(int id, String username, String fullName, String type) {
        setUserId(id);
        setUsername(username);
        setFullName(fullName);
        setUserType(type);
    }

    // --- Property Getters (Required for TableView & Binding) ---
    public IntegerProperty userIdProperty() { return userId; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty userTypeProperty() { return userType; }
    public StringProperty emailProperty() { return email; }
    public StringProperty phoneProperty() { return phone; }

    // --- Standard Getters and Setters ---
    public int getUserId() { return userId.get(); }
    public void setUserId(int value) { userId.set(value); }

    public String getUsername() { return username.get(); }
    public void setUsername(String value) { username.set(value); }

    public String getFullName() { return fullName.get(); }
    public void setFullName(String value) { fullName.set(value); }

    public String getUserType() { return userType.get(); }
    public void setUserType(String value) { userType.set(value); }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }

    public String getPhone() { return phone.get(); }
    public void setPhone(String value) { phone.set(value); }

    /**
     * Helper to verify if the current user has administrative access.
     */
    public boolean isStaff() {
        return "Staff".equalsIgnoreCase(getUserType());
    }

    @Override
    public String toString() {
        return getFullName() + " (" + getUserType() + ")";
    }
}