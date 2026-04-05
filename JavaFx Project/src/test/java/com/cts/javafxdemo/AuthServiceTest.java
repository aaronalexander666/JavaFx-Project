package com.cts.javafxdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuthService Tests")
class AuthServiceTest {
    private AuthService service = new AuthService();

    @Test
    void testLoginValidCredentials() {
        assertDoesNotThrow(() -> service.login("user", "pass", "Customer"));
    }

    @Test
    void testLoginNullUsername() {
        assertFalse(service.login(null, "pass", "Customer"));
    }

    @Test
    void testRegisterValidUser() {
        boolean result = service.registerCustomer("newuser", "pass1234", "User Name", "test@test.com", "5551234567", "123 Main");
        assertNotNull(result);
    }

    @Test
    void testRegisterShortPassword() {
        boolean result = service.registerCustomer("user", "123", "Name", "email@test.com", "123", "addr");
        assertFalse(result);
    }

    @Test
    void testSessionInitialState() {
        assertFalse(AuthService.isLoggedIn());
    }
}
