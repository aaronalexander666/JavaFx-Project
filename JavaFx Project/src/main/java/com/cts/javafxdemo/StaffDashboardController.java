package com.cts.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.cts.javafxdemo.cell.StatusCellFactory;
import com.cts.javafxdemo.models.Booking;

public class StaffDashboardController {

    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, String> colBookingId;
    @FXML private TableColumn<Booking, String> colCustomer;
    @FXML private TableColumn<Booking, String> colRoom;
    @FXML private TableColumn<Booking, String> colDates;
    @FXML private TableColumn<Booking, String> colStatus;
    @FXML private TableColumn<Booking, Double> colAmount;

    @FXML private Button btnConfirm, btnCheckIn, btnCheckOut, btnCancel, btnRefresh;

    private final BookingService bookingService = new BookingService();
    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBookings();
    }

    private void setupTableColumns() {
        colStatus.setCellFactory(new StatusCellFactory());
        bookingsTable.setItems(bookings);
    }

    private void loadBookings() {
        bookings.clear();
        bookings.addAll(bookingService.getAllBookings());
    }

    @FXML
    private void handleConfirm() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookingService.confirmBooking(selected.getBookingId());
            loadBookings();
        }
    }

    @FXML
    private void handleCheckIn() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookingService.checkInBooking(selected.getBookingId());
            loadBookings();
        }
    }

    @FXML
    private void handleCheckOut() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookingService.checkOutBooking(selected.getBookingId());
            loadBookings();
        }
    }

    @FXML
    private void handleCancel() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookingService.cancelBooking(selected.getBookingId());
            loadBookings();
        }
    }

    @FXML
    private void handleRefresh() {
        loadBookings();
    }
}