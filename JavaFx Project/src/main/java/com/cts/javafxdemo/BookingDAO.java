package com.cts.javafxdemo;

import com.cts.javafxdemo.models.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

public class BookingDAO {

    /**
     * Creates a new booking.
     * Uses a single transaction to ensure Room and Booking stay in sync.
     */
    public boolean createBooking(int customerId, int roomId, LocalDate checkIn,
                                 LocalDate checkOut, double totalAmount) {

        String insertBooking = "INSERT INTO tblbooking (customer_id, room_id, check_in_date, " +
                "check_out_date, total_amount, status, booking_date) " +
                "VALUES (?, ?, ?, ?, ?, 'Pending', NOW())";

        String reserveRoom = "UPDATE tblroom SET status = 'Reserved' WHERE room_id = ? AND status = 'Available'";

        try (Connection conn = DatabaseConnection.openConnection()) {
            conn.setAutoCommit(false); // Begin Transaction

            try (PreparedStatement roomPs = conn.prepareStatement(reserveRoom);
                 PreparedStatement bookingPs = conn.prepareStatement(insertBooking)) {

                // 1. Attempt to reserve the room first (concurrency check)
                roomPs.setInt(1, roomId);
                int roomUpdated = roomPs.executeUpdate();

                if (roomUpdated == 0) {
                    // Room might have been taken while user was looking at the screen
                    conn.rollback();
                    return false;
                }

                // 2. Insert the booking record
                bookingPs.setInt(1, customerId);
                bookingPs.setInt(2, roomId);
                bookingPs.setDate(3, java.sql.Date.valueOf(checkIn));
                bookingPs.setDate(4, java.sql.Date.valueOf(checkOut));
                bookingPs.setDouble(5, totalAmount);

                bookingPs.executeUpdate();

                conn.commit(); // End Transaction
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Transaction Failure: " + e.getMessage());
            return false;
        }
    }

    /**
     * Optimized Join for Staff View.
     * Maps Customer, Room, and Booking data in one indexed pass.
     */
    public ObservableList<Booking> getAllBookings() {
        ObservableList<Booking> list = FXCollections.observableArrayList();
        String sql = "SELECT b.*, c.full_name, c.email, r.room_number, r.room_type, r.price_per_night " +
                "FROM tblbooking b " +
                "INNER JOIN tblcustomer c ON b.customer_id = c.customer_id " +
                "INNER JOIN tblroom r ON b.room_id = r.room_id " +
                "ORDER BY b.booking_date DESC";

        try (Connection conn = DatabaseConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Centralized Mapping Logic.
     * Leverages the internal calculateBookingLogic() of your model.
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id"));
        b.setCustomerName(rs.getString("full_name"));
        b.setCustomerEmail(rs.getString("email"));
        b.setRoomNumber(rs.getString("room_number"));
        b.setRoomType(rs.getString("room_type"));
        b.setPricePerNight(rs.getDouble("price_per_night"));
        b.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        b.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        b.setStatus(rs.getString("status"));
        b.setTotalAmount(rs.getDouble("total_amount"));

        // Let the model handle the 'thick' logic for nights/amounts
        b.calculateBookingLogic();
        return b;
    }
    public ObservableList<Booking> getCustomerBookings(int customerId) {
        return getAllBookings();
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        return false;
    }

    public boolean cancelBooking(int bookingId) {
        return updateBookingStatus(bookingId, "Cancelled");
    }
}
