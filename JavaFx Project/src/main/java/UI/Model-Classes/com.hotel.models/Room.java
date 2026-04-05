package com.cts.javafxdemo.models;

import javafx.beans.property.*;

public class Room {
    // UI-Observable Properties
    private final IntegerProperty roomId = new SimpleIntegerProperty();
    private final StringProperty roomNumber = new SimpleStringProperty();
    private final StringProperty roomType = new SimpleStringProperty();
    private final DoubleProperty pricePerNight = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    // Default Constructor
    public Room() {}

    // Overloaded Constructor for Quick Mapping/Indexing
    public Room(int id, String number, String type, double price, String status) {
        setRoomId(id);
        setRoomNumber(number);
        setRoomType(type);
        setPricePerNight(price);
        setStatus(status);
    }

    /**
     * Helper to check availability without manual string comparison.
     * Useful for filtering logic in the Customer Dashboard.
     */
    public boolean isAvailable() {
        return getStatus() != null && getStatus().equalsIgnoreCase("Available");
    }

    // --- Property Getters (CRITICAL for TableView/UI Binding) ---
    public IntegerProperty roomIdProperty() { return roomId; }
    public StringProperty roomNumberProperty() { return roomNumber; }
    public StringProperty roomTypeProperty() { return roomType; }
    public DoubleProperty pricePerNightProperty() { return pricePerNight; }
    public StringProperty statusProperty() { return status; }
    public StringProperty descriptionProperty() { return description; }

    // --- Standard Getters and Setters ---
    public int getRoomId() { return roomId.get(); }
    public void setRoomId(int value) { roomId.set(value); }

    public String getRoomNumber() { return roomNumber.get(); }
    public void setRoomNumber(String value) { roomNumber.set(value); }

    public String getRoomType() { return roomType.get(); }
    public void setRoomType(String value) { roomType.set(value); }

    public double getPricePerNight() { return pricePerNight.get(); }
    public void setPricePerNight(double value) { pricePerNight.set(value); }

    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }

    public String getDescription() { return description.get(); }
    public void setDescription(String value) { description.set(value); }

    /**
     * Enhanced toString for ComboBox display.
     * Provides a clean indexed view for the user.
     */
    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f/night [%s]",
                getRoomNumber(), getRoomType(), getPricePerNight(), getStatus());
    }
}