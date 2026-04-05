package com.cts.javafxdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;  
import java.time.LocalDate;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Stress Tests")
class StressTest {
    
    @Test
    void testConcurrentBookingCreation() throws InterruptedException {
        BookingService service = new BookingService();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(50);
        
        for (int i = 0; i < 50; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    LocalDate checkIn = LocalDate.now().plusDays(threadId);
                    LocalDate checkOut = checkIn.plusDays(1);
                    service.createBooking(1, (threadId % 5) + 1, checkIn, checkOut);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        assertTrue(latch.await(30, TimeUnit.SECONDS), "All bookings should complete");
        executor.shutdown();
    }

    @Test
    void testRapidBookingCalculations() {
        BookingService service = new BookingService();
        LocalDate baseDate = LocalDate.now().plusDays(30);
        
        for (int i = 0; i < 1000; i++) {
            LocalDate checkIn = baseDate.plusDays(i % 30);
            LocalDate checkOut = checkIn.plusDays((i % 10) + 1);
            double total = service.calculateTotal((i % 5) + 1, checkIn, checkOut);
            assertTrue(total >= 0, "Calculation should return non-negative value");
        }
    }
}
