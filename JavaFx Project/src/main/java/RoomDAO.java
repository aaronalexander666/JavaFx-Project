package com.cts.javafxdemo;

import com.cts.javafxdemo.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

/**
 * Data Access Object for Room-related operations.
 */
public class RoomDAO {

    private final DatabaseConnection dbConnection = new DatabaseConnection();

    /**
     * Retrieves all available rooms for a given date range.
     */
    public ObservableList<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        String sql = "SELECT r.* FROM tblroom r " +
                "WHERE r.status = 'Available' " +
                "AND r.room_id NOT IN (" +
                "    SELECT b.room_id FROM tblbooking b " +
                "    WHERE b.status IN ('Confirmed', 'Checked In') " +
                "    AND ((b.check_in_date BETWEEN ? AND ?) " +
                "    OR (b.check_out_date BETWEEN ? AND ?) " +
                "    OR (b.check_in_date <= ? AND b.check_out_date >= ?))" +
                ")";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            java.sql.Date checkInDate = java.sql.Date.valueOf(checkIn);
            java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOut);

            ps.setDate(1, checkInDate);
            ps.setDate(2, checkOutDate);
            ps.setDate(3, checkInDate);
            ps.setDate(4, checkOutDate);
            ps.setDate(5, checkInDate);
            ps.setDate(6, checkOutDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setRoomType(rs.getString("room_type"));
                    room.setPricePerNight(rs.getDouble("price_per_night"));
                    room.setStatus(rs.getString("status"));
                    room.setDescription(rs.getString("description"));
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            System.err.println("Room Retrieval Error: " + e.getMessage());
        }

        return rooms;
    }

    /**
     * Gets room by ID.
     */
    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM tblroom WHERE room_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setRoomType(rs.getString("room_type"));
                    room.setPricePerNight(rs.getDouble("price_per_night"));
                    room.setStatus(rs.getString("status"));
                    return room;
                }
            }
        } catch (SQLException e) {
            System.err.println("Get Room Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates room status.
     */
    public boolean updateRoomStatus(int roomId, String status) {
        String sql = "UPDATE tblroom SET status = ? WHERE room_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, roomId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update Room Status Error: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Room> getAllRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tblroom";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setPricePerNight(rs.getDouble("price_per_night"));
                room.setStatus(rs.getString("status"));
                room.setDescription(rs.getString("description"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Get All Rooms Error: " + e.getMessage());
        }
        return rooms;
    }
}