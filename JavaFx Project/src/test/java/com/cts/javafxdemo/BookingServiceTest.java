package com.cts.javafxdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookingService Tests")
class BookingServiceTest {
    private BookingService service = new BookingService();

    @Test
    void testCalculateTotalOneNight() {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(1);
        double total = service.calculateTotal(1, checkIn, checkOut);
        assertTrue(total >= 0, "Total should be non-negative");
    }

    @Test
    void testCalculateTotalMultipleNights() {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(5);
        double total = service.calculateTotal(1, checkIn, checkOut);
        assertTrue(total >= 0, "Total for 5 nights should be non-negative");
    }

    @Test
    void testCreateBooking() {
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(3);
        assertDoesNotThrow(() -> service.createBooking(1, 1, checkIn, checkOut));
    }

    @Test
    void testConfirmBooking() {
        assertDoesNotThrow(() -> service.confirmBooking(1));
    }

    @Test
    void testCheckInBooking() {
        assertDoesNotThrow(() -> service.checkInBooking(1));
    }
}
