package com.cts.javafxdemo.models;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    // UI-Observable Properties
    private final IntegerProperty bookingId = new SimpleIntegerProperty();
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final IntegerProperty roomId = new SimpleIntegerProperty();
    private final StringProperty roomNumber = new SimpleStringProperty();
    private final StringProperty roomType = new SimpleStringProperty();
    private final DoubleProperty pricePerNight = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> checkInDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> checkOutDate = new SimpleObjectProperty<>();
    private final DoubleProperty totalAmount = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> bookingDate = new SimpleObjectProperty<>();
    private final StringProperty customerName = new SimpleStringProperty();
    private final StringProperty customerEmail = new SimpleStringProperty();
    private final StringProperty customerPhone = new SimpleStringProperty();
    private int nights;

    // Default Constructor
    public Booking() {}

    // Overloaded Constructor for Quick Mapping/Indexing
    public Booking(int id, String name, String roomNum, LocalDate checkIn, LocalDate checkOut, double price) {
        setBookingId(id);
        setCustomerName(name);
        setRoomNumber(roomNum);
        setCheckInDate(checkIn);
        setCheckOutDate(checkOut);
        setPricePerNight(price);
        calculateBookingLogic();
        setStatus("Pending");
        setBookingDate(LocalDateTime.now());
    }

    /**
     * Internal logic for "thick" data mapping.
     * Automatically calculates nights and total amount based on dates.
     */
    public void calculateBookingLogic() {
        if (getCheckInDate() != null && getCheckOutDate() != null) {
            this.nights = (int) ChronoUnit.DAYS.between(getCheckInDate(), getCheckOutDate());
            // Ensure nights is at least 1 for billing purposes
            this.nights = Math.max(this.nights, 1);
            setTotalAmount(this.nights * getPricePerNight());
        }
    }

    // --- Property Getters (CRITICAL for TableView binding) ---
    public IntegerProperty bookingIdProperty() { return bookingId; }
    public StringProperty customerNameProperty() { return customerName; }
    public StringProperty roomNumberProperty() { return roomNumber; }
    public StringProperty statusProperty() { return status; }
    public DoubleProperty totalAmountProperty() { return totalAmount; }
    public ObjectProperty<LocalDate> checkInDateProperty() { return checkInDate; }
    public ObjectProperty<LocalDate> checkOutDateProperty() { return checkOutDate; }

    // --- Standard Getters and Setters ---
    public int getBookingId() { return bookingId.get(); }
    public void setBookingId(int value) { bookingId.set(value); }

    public int getCustomerId() { return customerId.get(); }
    public void setCustomerId(int value) { customerId.set(value); }

    public int getRoomId() { return roomId.get(); }
    public void setRoomId(int value) { roomId.set(value); }

    public String getRoomNumber() { return roomNumber.get(); }
    public void setRoomNumber(String value) { roomNumber.set(value); }

    public String getRoomType() { return roomType.get(); }
    public void setRoomType(String value) { roomType.set(value); }

    public double getPricePerNight() { return pricePerNight.get(); }
    public void setPricePerNight(double value) { pricePerNight.set(value); }

    public LocalDate getCheckInDate() { return checkInDate.get(); }
    public void setCheckInDate(LocalDate value) { checkInDate.set(value); }

    public LocalDate getCheckOutDate() { return checkOutDate.get(); }
    public void setCheckOutDate(LocalDate value) { checkOutDate.set(value); }

    public double getTotalAmount() { return totalAmount.get(); }
    public void setTotalAmount(double value) { totalAmount.set(value); }

    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }

    public LocalDateTime getBookingDate() { return bookingDate.get(); }
    public void setBookingDate(LocalDateTime value) { bookingDate.set(value); }

    public String getCustomerName() { return customerName.get(); }
    public void setCustomerName(String value) { customerName.set(value); }

    public String getCustomerEmail() { return customerEmail.get(); }
    public void setCustomerEmail(String value) { customerEmail.set(value); }

    public String getCustomerPhone() { return customerPhone.get(); }
    public void setCustomerPhone(String value) { customerPhone.set(value); }

    public int getNights() { return nights; }
    public void setNights(int value) { nights = value; }
}
