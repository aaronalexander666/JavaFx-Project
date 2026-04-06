package com.cts.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import com.cts.javafxdemo.models.Booking;

public class CustomerDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Booking, String> colRoom;
    @FXML private TableColumn<Booking, String> colDates;
    @FXML private TableColumn<Booking, String> colStatus;
    @FXML private TableColumn<Booking, Double> colTotal;

    @FXML private DatePicker checkInPicker, checkOutPicker;
    @FXML private ComboBox<String> roomComboBox;
    @FXML private Label totalLabel;
    @FXML private Button btnSearchRooms, btnBookRoom, btnCancelBooking;

    private final BookingService bookingService = new BookingService();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private final ObservableList<String> availableRooms = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        loadCustomerBookings();
        loadAvailableRooms();
    }

    private void setupTable() {
        bookingsTable.setItems(bookings);
    }

    private void loadCustomerBookings() {
        bookings.clear();
        // In a real app, get current customer ID from session
        bookings.addAll(bookingService.getCustomerBookings(1)); // Default customer ID
    }

    private void loadAvailableRooms() {
        availableRooms.clear();
        // Load available rooms
        roomComboBox.setItems(availableRooms);
    }

    @FXML
    private void handleSearchRooms() {
        // Implement room search logic
        updateTotal();
    }

    @FXML
    private void handleBookRoom() {
        LocalDate checkIn = checkInPicker.getValue();
        LocalDate checkOut = checkOutPicker.getValue();
        String selectedRoom = roomComboBox.getValue();

        if (checkIn != null && checkOut != null && selectedRoom != null) {
            // Parse room ID from selected room string
            int roomId = Integer.parseInt(selectedRoom.split(" - ")[0]);
            double total = bookingService.calculateTotal(roomId, checkIn, checkOut);

            if (bookingService.createBooking(1, roomId, checkIn, checkOut)) { // Default customer ID
                loadCustomerBookings();
                loadAvailableRooms();
                showAlert("Success", "Booking created successfully!");
            } else {
                showAlert("Error", "Failed to create booking. Room may not be available.");
            }
        }
    }

    @FXML
    private void handleCancelBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Booking");
            alert.setHeaderText("Are you sure you want to cancel this booking?");
            alert.setContentText("This action cannot be undone.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                bookingService.cancelBooking(selected.getBookingId());
                loadCustomerBookings();
                loadAvailableRooms();
            }
        }
    }

    private void updateTotal() {
        LocalDate checkIn = checkInPicker.getValue();
        LocalDate checkOut = checkOutPicker.getValue();
        String selectedRoom = roomComboBox.getValue();

        if (checkIn != null && checkOut != null && selectedRoom != null) {
            try {
                int roomId = Integer.parseInt(selectedRoom.split(" - ")[0]);
                double total = bookingService.calculateTotal(roomId, checkIn, checkOut);
                totalLabel.setText(String.format("$%.2f", total));
            } catch (Exception e) {
                totalLabel.setText("$0.00");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}