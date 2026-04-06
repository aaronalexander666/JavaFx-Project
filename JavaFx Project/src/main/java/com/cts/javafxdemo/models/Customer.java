package com.cts.javafxdemo.models;

import javafx.beans.property.*;

/**
 * Customer Model - Represents a guest in the Hotel Management System.
 * mapped to 'tblcustomer' in the javafxdemo database.
 */
public class Customer {
    // Primary Key and Identity
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();

    // Contact Information
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty mobilePhone = new SimpleStringProperty();

    // Address Mapping (Flattened for easier Database Indexing)
    private final StringProperty street = new SimpleStringProperty();
    private final StringProperty village = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final StringProperty country = new SimpleStringProperty();

    // System Status
    private final BooleanProperty active = new SimpleBooleanProperty(true);

    // Default Constructor for DAO Mapping
    public Customer() {}

    /**
     * Overloaded Constructor for Quick Registration / Reports.
     */
    public Customer(int id, String fName, String lName, String email, String phone) {
        setCustomerId(id);
        setFirstName(fName);
        setLastName(lName);
        setEmail(email);
        setMobilePhone(phone);
    }

    // --- Property Getters (Crucial for TableView Data-Binding) ---
    public IntegerProperty customerIdProperty() { return customerId; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty emailProperty() { return email; }
    public StringProperty mobilePhoneProperty() { return mobilePhone; }
    public StringProperty streetProperty() { return street; }
    public StringProperty villageProperty() { return village; }
    public StringProperty cityProperty() { return city; }
    public StringProperty countryProperty() { return country; }
    public BooleanProperty activeProperty() { return active; }

    // --- Standard Getters and Setters ---
    public int getCustomerId() { return customerId.get(); }
    public void setCustomerId(int value) { customerId.set(value); }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String value) { firstName.set(value); }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String value) { lastName.set(value); }

    /**
     * Helper to get the full display name for reports and UI labels.
     */
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }

    public String getMobilePhone() { return mobilePhone.get(); }
    public void setMobilePhone(String value) { mobilePhone.set(value); }

    public String getCity() { return city.get(); }
    public void setCity(String value) { city.set(value); }

    public String getCountry() { return country.get(); }
    public void setCountry(String value) { country.set(value); }

    public boolean isActive() { return active.get(); }
    public void setActive(boolean value) { active.set(value); }

    /**
     * Optimized for Search and ComboBox indexing.
     */
    @Override
    public String toString() {
        return String.format("%s (ID: %d)", getFullName(), getCustomerId());
    }
}