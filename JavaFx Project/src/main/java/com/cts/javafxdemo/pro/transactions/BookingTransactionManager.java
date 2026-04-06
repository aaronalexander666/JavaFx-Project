package com.cts.javafxdemo.pro.transactions;

import com.cts.javafxdemo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles ACID booking transactions for check-in and check-out.
 * If either booking or room update fails, the entire transaction is rolled back.
 */
public final class BookingTransactionManager {

    private BookingTransactionManager() {
        // Prevent instantiation.
    }

    public static boolean executeCheckIn(int bookingId) {
        String updateBooking = "UPDATE tblbooking SET status='Checked-In', check_in_time=NOW() WHERE bookingid=?";
        String updateRoom = "UPDATE tblroom SET status='Occupied' WHERE roomid = " +
                "(SELECT roomid FROM tblbooking WHERE bookingid=?)";
        return executeAtomicUpdate(bookingId, updateBooking, updateRoom);
    }

    public static boolean executeCheckOut(int bookingId) {
        String updateBooking = "UPDATE tblbooking SET status='Checked-Out', check_out_time=NOW() WHERE bookingid=?";
        String updateRoom = "UPDATE tblroom SET status='Cleaning' WHERE roomid = " +
                "(SELECT roomid FROM tblbooking WHERE bookingid=?)";
        return executeAtomicUpdate(bookingId, updateBooking, updateRoom);
    }

    private static boolean executeAtomicUpdate(int bookingId, String bookingSql, String roomSql) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement bookingStmt = conn.prepareStatement(bookingSql);
                 PreparedStatement roomStmt = conn.prepareStatement(roomSql)) {

                bookingStmt.setInt(1, bookingId);
                roomStmt.setInt(1, bookingId);

                int bookingRows = bookingStmt.executeUpdate();
                int roomRows = roomStmt.executeUpdate();

                if (bookingRows > 0 && roomRows > 0) {
                    conn.commit();
                    return true;
                }

                conn.rollback();
                return false;
            } catch (SQLException innerException) {
                try {
                    if (conn != null) {
                        conn.rollback();
                    }
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
                innerException.printStackTrace();
                return false;
            }
        } catch (SQLException outerException) {
            outerException.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
        }
    }
}
