package com.cts.javafxdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;

import java.time.LocalDate;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Stress Tests for Hotel App - PRODUCTION READINESS
 * Tests performance under high load and concurrent operations
 */
@DisplayName("Hotel App Stress Tests")
class HotelAppStressTest {

    private AuthService authService;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        bookingService = new BookingService();
    }

    // ========== CONCURRENT LOGIN STRESS TESTS ==========

    @Test
    @DisplayName("Concurrent login attempts - 50 threads")
    @Timeout(30)
    void testConcurrentLoginAttempts50() throws InterruptedException {
        // Arrange
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        // Act
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    String username = "user_" + threadId;
                    String password = "password_" + threadId;
                    boolean result = authService.login(username, password, "Customer");
                    
                    if (result) {
                        successCount.incrementAndGet();
                    } else {
                        failureCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Assert
        boolean completed = latch.await(30, TimeUnit.SECONDS);
        assertTrue(completed, "All login attempts should complete within timeout");
        executor.shutdown();
        
        int total = successCount.get() + failureCount.get();
        assertEquals(threadCount, total, "All threads should complete");
        assertTrue(total > 0, "At least some login attempts should complete");
    }

    @Test
    @DisplayName("Concurrent login attempts - 100 threads")
    @Timeout(60)
    void testConcurrentLoginAttempts100() throws InterruptedException {
        // Arrange
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger completedCount = new AtomicInteger(0);

        // Act
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    authService.login("user_" + threadId, "pass_" + threadId, "Customer");
                    completedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Assert
        boolean completed = latch.await(60, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - startTime;
        
        assertTrue(completed, "All login attempts should complete within timeout");
        assertEquals(threadCount, completedCount.get(), "All threads should complete");
        
        System.out.println("✓ 100 concurrent logins completed in " + duration + "ms");
        executor.shutdown();
    }

    // ========== CONCURRENT REGISTRATION STRESS TESTS ==========

    @Test
    @DisplayName("Concurrent registration attempts - 50 threads")
    @Timeout(30)
    void testConcurrentRegistration50() throws InterruptedException {
        // Arrange
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger completedCount = new AtomicInteger(0);

        // Act
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    authService.registerCustomer(
                        "newuser_" + threadId,
                        "password_abc123_" + threadId,
                        "User Name " + threadId,
                        "user" + threadId + "@example.com",
                        "555000" + threadId,
                        threadId + " Main Street"
                    );
                    completedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Assert
        boolean completed = latch.await(30, TimeUnit.SECONDS);
        assertTrue(completed, "All registration attempts should complete within timeout");
        assertEquals(threadCount, completedCount.get(), "All threads should complete");
        executor.shutdown();
    }

    // ========== CONCURRENT BOOKING STRESS TESTS ==========

    @Test
    @DisplayName("Concurrent booking creation - 50 threads")
    @Timeout(30)
    void testConcurrentBookingCreation50() throws InterruptedException {
        // Arrange
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger completedCount = new AtomicInteger(0);

        // Act
        LocalDate baseDate = LocalDate.now().plusDays(30);
        
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    LocalDate checkIn = baseDate.plusDays(threadId);
                    LocalDate checkOut = checkIn.plusDays(1);
                    
                    bookingService.createBooking(
                        1,  // customerId
                        (threadId % 5) + 1,  // roomId (1-5)
                        checkIn,
                        checkOut
                    );
                    completedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Assert
        boolean completed = latch.await(30, TimeUnit.SECONDS);
        assertTrue(completed, "All booking operations should complete within timeout");
        assertEquals(threadCount, completedCount.get(), "All threads should complete");
        executor.shutdown();
    }

    @Test
    @DisplayName("Concurrent booking creation - 100 threads")
    @Timeout(60)
    void testConcurrentBookingCreation100() throws InterruptedException {
        // Arrange
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger completedCount = new AtomicInteger(0);

        // Act
        long startTime = System.currentTimeMillis();
        LocalDate baseDate = LocalDate.now().plusDays(30);
        
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    LocalDate checkIn = baseDate.plusDays(threadId % 30);
                    LocalDate checkOut = checkIn.plusDays(1);
                    
                    bookingService.createBooking(
                        (threadId % 10) + 1,  // customerId (1-10)
                        (threadId % 5) + 1,    // roomId (1-5)
                        checkIn,
                        checkOut
                    );
                    completedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Assert
        boolean completed = latch.await(60, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - startTime;
        
        assertTrue(completed, "All booking operations should complete within timeout");
        assertEquals(threadCount, completedCount.get(), "All threads should complete");
        
        System.out.println("✓ 100 concurrent bookings created in " + duration + "ms");
        executor.shutdown();
    }

    // ========== RAPID SEQUENTIAL OPERATIONS ==========

    @Test
    @DisplayName("Rapid sequential booking calculations - 1000 operations")
    void testRapidBookingCalculations() {
        // Arrange
        long startTime = System.currentTimeMillis();
        LocalDate baseDate = LocalDate.now().plusDays(30);
        int operations = 1000;

        // Act
        for (int i = 0; i < operations; i++) {
            LocalDate checkIn = baseDate.plusDays(i % 30);
            LocalDate checkOut = checkIn.plusDays((i % 10) + 1);
            bookingService.calculateTotal(
                (i % 5) + 1,  // roomId
                checkIn,
                checkOut
            );
        }

        long duration = System.currentTimeMillis() - startTime;

        // Assert
        assertTrue(duration < 5000, "1000 calculations should complete in < 5 seconds");
        System.out.println("✓ 1000 booking calculations completed in " + duration + "ms");
    }

    @Test
    @DisplayName("Rapid sequential booking status updates - 500 operations")
    void testRapidBookingStatusUpdates() {
        // Arrange
        long startTime = System.currentTimeMillis();
        int operations = 500;

        // Act
        for (int i = 1; i <= operations; i++) {
            bookingService.confirmBooking(i);
            bookingService.checkInBooking(i);
            bookingService.checkOutBooking(i);
        }

        long duration = System.currentTimeMillis() - startTime;

        // Assert
        assertTrue(duration < 5000, "1500 status updates should complete in < 5 seconds");
        System.out.println("✓ 500 booking status sequences completed in " + duration + "ms");
    }

    // ========== MIXED OPERATION STRESS TEST ==========

    @Test
    @DisplayName("Mixed operations under load - 200 threads")
    @Timeout(120)
    void testMixedOperationsUnderLoad() throws InterruptedException {
        // Arrange
        int threadCount = 200;
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger completedCount = new AtomicInteger(0);

        // Act
        long startTime = System.currentTimeMillis();
        LocalDate baseDate = LocalDate.now().plusDays(30);
        
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    int operationType = threadId % 3;
                    
                    switch (operationType) {
                        case 0: // Login
                            authService.login("user_" + threadId, "pass", "Customer");
                            break;
                        case 1: // Register
                            authService.registerCustomer(
                                "newuser_" + threadId,
                                "password123",
                                "User " + threadId,
                                "user" + threadId + "@test.com",
                                "5550000",
                                "123 Street"
                            );
                            break;
                        case 2: // Booking
                            LocalDate checkIn = baseDate.plusDays(threadId % 30);
                            LocalDate checkOut = checkIn.plusDays(1);
                            bookingService.createBooking(1, (threadId % 5) + 1, checkIn, checkOut);
                            break;
                    }
                    completedCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        // Assert
        boolean completed = latch.await(120, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - startTime;
        
        assertTrue(completed, "All mixed operations should complete within timeout");
        assertEquals(threadCount, completedCount.get(), "All threads should complete");
        
        System.out.println("✓ 200 mixed operations completed in " + duration + "ms");
        executor.shutdown();
    }

    // ========== MEMORY AND RESOURCE STRESS ==========

    @Test
    @DisplayName("Large dataset processing - 10000 booking calculations")
    void testLargeDatasetProcessing() {
        // Arrange
        long startTime = System.currentTimeMillis();
        LocalDate baseDate = LocalDate.now().plusDays(30);
        int operations = 10000;
        AtomicInteger completedCount = new AtomicInteger(0);

        // Act
        for (int i = 0; i < operations; i++) {
            LocalDate checkIn = baseDate.plusDays(i % 365);
            LocalDate checkOut = checkIn.plusDays((i % 10) + 1);
            
            double total = bookingService.calculateTotal(
                (i % 20) + 1,  // roomId 1-20
                checkIn,
                checkOut
            );
            
            if (total >= 0) {
                completedCount.incrementAndGet();
            }
        }

        long duration = System.currentTimeMillis() - startTime;

        // Assert
        assertEquals(operations, completedCount.get(), "All operations should complete");
        assertTrue(duration < 30000, "10000 calculations should complete in < 30 seconds");
        
        System.out.println("✓ 10000 booking calculations completed in " + duration + "ms");
        System.out.println("  Average: " + (duration / (double) operations) + "ms per operation");
    }

}
