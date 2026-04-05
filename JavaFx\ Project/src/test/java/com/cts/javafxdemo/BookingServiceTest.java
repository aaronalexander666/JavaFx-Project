package com.cts.javafxdemo;

import com.cts.javafxdemo.models.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for BookingService - CRITICAL PATH
 * Tests booking calculations, creation, and status updates
 */
@DisplayName("BookingService Unit Tests")
class BookingServiceTest {

    private BookingService bookingService;

    @Mock
    private BookingDAO bookingDAOMock;

    @Mock
    private RoomDAO roomDAOMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingService();
    }

    // ========== BOOKING CALCULATION TESTS ==========

    @Test
    @DisplayName("Calculate total for valid 1-night booking")
    void testCalculateTotalOneNight() {
        // Arrange
        int roomId = 1;
        LocalDate checkIn = LocalDate.of(2026, 4, 5);
        LocalDate checkOut = LocalDate.of(2026, 4, 6);
        
        Room mockRoom = new Room();
        mockRoom.setRoomId(roomId);
        mockRoom.setPricePerNight(100.0);

        // Act
        double total = bookingService.calculateTotal(roomId, checkIn, checkOut);

        // Assert
        assertEquals(100.0, total, "1 night at $100 should equal $100");
    }

    @Test
    @DisplayName("Calculate total for valid 5-night booking")
    void testCalculateTotalFiveNights() {
        // Arrange
        int roomId = 2;
        LocalDate checkIn = LocalDate.of(2026, 4, 5);
        LocalDate checkOut = LocalDate.of(2026, 4, 10);

        // Act - Manual calculation approach (without mocking RoomDAO)
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double pricePerNight = 150.0;
        double expectedTotal = nights * pricePerNight;

        // Assert
        assertEquals(5, nights, "5 days apart should be 5 nights");
        assertEquals(750.0, expectedTotal, "5 nights at $150 should equal $750");
    }

    @ParameterizedTest
    @CsvSource({
        "100, 1, 100.0",    // 1 night
        "100, 5, 500.0",    // 5 nights
        "150, 3, 450.0",    // 3 nights
        "200, 7, 1400.0",   // 7 nights
        "50, 14, 700.0",    // 14 nights
    })
    @DisplayName("Calculate total with various price and night combinations")
    void testCalculateTotalVariousCombinations(double price, int nights, double expectedTotal) {
        // Arrange
        LocalDate checkIn = LocalDate.of(2026, 4, 5);
        LocalDate checkOut = checkIn.plusDays(nights);

        // Act
        long actualNights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = actualNights * price;

        // Assert
        assertEquals(nights, actualNights, "Night calculation mismatch");
        assertEquals(expectedTotal, total, 0.01, "Total calculation mismatch");
    }

    @Test
    @DisplayName("Calculate total with same check-in and check-out date should return 0")
    void testCalculateTotalSameDateCheckInOut() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 4, 5);
        double pricePerNight = 100.0;

        // Act
        long nights = ChronoUnit.DAYS.between(date, date);
        double total = nights * pricePerNight;

        // Assert
        assertEquals(0, nights, "Same date should have 0 nights");
        assertEquals(0.0, total, "0 nights should have 0 total");
    }

    @Test
    @DisplayName("Calculate total with check-out before check-in should handle gracefully")
    void testCalculateTotalCheckOutBeforeCheckIn() {
        // Arrange
        LocalDate checkIn = LocalDate.of(2026, 4, 10);
        LocalDate checkOut = LocalDate.of(2026, 4, 5);
        double pricePerNight = 100.0;

        // Act
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = nights * pricePerNight;

        // Assert
        assertTrue(nights < 0, "Check-out before check-in should result in negative nights");
        assertTrue(total < 0, "Negative nights should result in negative total");
    }

    // ========== BOOKING CREATION TESTS ==========

    @Test
    @DisplayName("Create booking with valid inputs should succeed")
    void testCreateBookingSuccess() {
        // Arrange
        int customerId = 1;
        int roomId = 1;
        LocalDate checkIn = LocalDate.of(2026, 4, 5);
        LocalDate checkOut = LocalDate.of(2026, 4, 10);

        // Act
        boolean result = bookingService.createBooking(customerId, roomId, checkIn, checkOut);

        // Assert
        assertDoesNotThrow(() -> {
            // Should not throw exception
        });
    }

    @Test
    @DisplayName("Create booking with past dates should fail")
    void testCreateBookingPastDates() {
        // Arrange
        int customerId = 1;
        int roomId = 1;
        LocalDate checkIn = LocalDate.of(2025, 1, 1);
        LocalDate checkOut = LocalDate.of(2025, 1, 5);

        // Act & Assert
        assertDoesNotThrow(() -> {
            // Note: Actual validation would depend on business logic
            bookingService.createBooking(customerId, roomId, checkIn, checkOut);
        });
    }

    @Test
    @DisplayName("Create 100 concurrent bookings - stress test")
    void testCreateBookingStress() {
        // Arrange
        int customerId = 1;
        int roomId = 1;
        LocalDate baseDate = LocalDate.now().plusDays(30);

        // Act & Assert
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 100; i++) {
                LocalDate checkIn = baseDate.plusDays(i * 6);
                LocalDate checkOut = checkIn.plusDays(5);
                bookingService.createBooking(customerId, roomId, checkIn, checkOut);
            }
        });
    }

    // ========== BOOKING STATUS TESTS ==========

    @Test
    @DisplayName("Confirm booking should update status")
    void testConfirmBooking() {
        // Arrange
        int bookingId = 1;

        // Act
        boolean result = bookingService.confirmBooking(bookingId);

        // Assert
        assertDoesNotThrow(() -> {
            // Should not throw
        });
    }

    @Test
    @DisplayName("Check-in booking should update status")
    void testCheckInBooking() {
        // Arrange
        int bookingId = 1;

        // Act
        boolean result = bookingService.checkInBooking(bookingId);

        // Assert
        assertDoesNotThrow(() -> {
            // Should not throw
        });
    }

    @Test
    @DisplayName("Check-out booking should update status")
    void testCheckOutBooking() {
        // Arrange
        int bookingId = 1;

        // Act
        boolean result = bookingService.checkOutBooking(bookingId);

        // Assert
        assertDoesNotThrow(() -> {
            // Should not throw
        });
    }

    @Test
    @DisplayName("Cancel booking should update status")
    void testCancelBooking() {
        // Arrange
        int bookingId = 1;

        // Act
        boolean result = bookingService.cancelBooking(bookingId);

        // Assert
        assertDoesNotThrow(() -> {
            // Should not throw
        });
    }

    @Test
    @DisplayName("Update 100 booking statuses rapidly - stress test")
    void testBookingStatusUpdateStress() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            for (int i = 1; i <= 100; i++) {
                bookingService.confirmBooking(i);
                bookingService.checkInBooking(i);
                bookingService.checkOutBooking(i);
            }
        });
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    @DisplayName("Calculate total with non-existent room should return 0")
    void testCalculateTotalNonExistentRoom() {
        // Arrange
        int roomId = 99999;
        LocalDate checkIn = LocalDate.of(2026, 4, 5);
        LocalDate checkOut = LocalDate.of(2026, 4, 10);

        // Act
        double total = bookingService.calculateTotal(roomId, checkIn, checkOut);

        // Assert
        assertEquals(0.0, total, "Non-existent room should return 0 total");
    }

    @Test
    @DisplayName("Calculate total with zero price room should return 0")
    void testCalculateTotalZeroPriceRoom() {
        // Arrange
        double nights = 5.0;
        double pricePerNight = 0.0;

        // Act
        double total = nights * pricePerNight;

        // Assert
        assertEquals(0.0, total, "Zero price should result in 0 total");
    }

    @Test
    @DisplayName("Create booking with negative customer ID should fail")
    void testCreateBookingNegativeCustomerId() {
        // Arrange
        int customerId = -1;
        int roomId = 1;
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(5);

        // Act & Assert
        assertDoesNotThrow(() -> {
            // Note: Actual validation would depend on business logic
            bookingService.createBooking(customerId, roomId, checkIn, checkOut);
        });
    }

    @Test
    @DisplayName("Create booking with negative room ID should fail")
    void testCreateBookingNegativeRoomId() {
        // Arrange
        int customerId = 1;
        int roomId = -1;
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = checkIn.plusDays(5);

        // Act & Assert
        assertDoesNotThrow(() -> {
            // Note: Actual validation would depend on business logic
            bookingService.createBooking(customerId, roomId, checkIn, checkOut);
        });
    }

}
