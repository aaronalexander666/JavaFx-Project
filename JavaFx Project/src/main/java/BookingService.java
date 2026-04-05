Package com.cts.javafxdemo;

import com.cts.javafxdemo.models.Booking;
import com.cts.javafxdemo.models.Room;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Booking Service - Business Logic Layer
 */
public class BookingService {

    private final BookingDAO bookingDAO = new BookingDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    /**
     * Calculates total amount for a booking.
     */
    public double calculateTotal(int roomId, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomDAO.getRoomById(roomId);
        if (room == null) return 0.0;

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) return 0.0;

        return nights * room.getPricePerNight();
    }

    /**
     * Creates a new booking.
     */
    public boolean createBooking(int customerId, int roomId, LocalDate checkIn,
                                 LocalDate checkOut) {
        double total = calculateTotal(roomId, checkIn, checkOut);
        if (total <= 0) return false;

        return bookingDAO.createBooking(customerId, roomId, checkIn, checkOut, total);
    }

    /**
     * Gets customer bookings.
     */
    public ObservableList<Booking> getCustomerBookings(int customerId) {
        return bookingDAO.getCustomerBookings(customerId);
    }

    /**
     * Gets all bookings (staff).
     */
    public ObservableList<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    /**
     * Confirms a booking.
     */
    public boolean confirmBooking(int bookingId) {
        return bookingDAO.updateBookingStatus(bookingId, "Confirmed");
    }

    /**
     * Checks in a booking.
     */
    public boolean checkInBooking(int bookingId) {
        return bookingDAO.updateBookingStatus(bookingId, "Checked In");
    }

    /**
     * Checks out a booking.
     */
    public boolean checkOutBooking(int bookingId) {
        return bookingDAO.updateBookingStatus(bookingId, "Checked Out");
    }

    /**
     * Cancels a booking.
     */
    public boolean cancelBooking(int bookingId) {
        return bookingDAO.cancelBooking(bookingId);
    }

    /**
     * Generates quote text for a booking.
     */
    public String generateQuote(Booking booking) {
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append("      HOTEL BOOKING QUOTATION\n");
        sb.append("=====================================\n\n");
        sb.append("Booking ID: ").append(booking.getBookingId()).append("\n");
        sb.append("Customer: ").append(booking.getCustomerName()).append("\n");
        sb.append("Email: ").append(booking.getCustomerEmail()).append("\n");
        sb.append("Phone: ").append(booking.getCustomerPhone()).append("\n\n");
        sb.append("Room Details:\n");
        sb.append("  Room Number: ").append(booking.getRoomNumber()).append("\n");
        sb.append("  Room Type: ").append(booking.getRoomType()).append("\n");
        sb.append("  Price/Night: $").append(String.format("%.2f", booking.getPricePerNight())).append("\n\n");
        sb.append("Stay Details:\n");
        sb.append("  Check-in: ").append(booking.getCheckInDate()).append("\n");
        sb.append("  Check-out: ").append(booking.getCheckOutDate()).append("\n");
        sb.append("  Nights: ").append(booking.getNights()).append("\n\n");
        sb.append("-------------------------------------\n");
        sb.append("TOTAL AMOUNT: $").append(String.format("%.2f", booking.getTotalAmount())).append("\n");
        sb.append("-------------------------------------\n");
        sb.append("Status: ").append(booking.getStatus()).append("\n");
        sb.append("Booking Date: ").append(booking.getBookingDate()).append("\n\n");
        sb.append("Thank you for choosing our hotel!\n");

        return sb.toString();
    }
}