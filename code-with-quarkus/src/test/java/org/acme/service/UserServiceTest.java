package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.SignupRequest;
import org.acme.dto.SignupResponse;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserServiceTest {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        // Clean up test data before each test
        userRepository.deleteAll();
    }

    @Test
    void testSignupSuccess() {
        // Given
        SignupRequest request = new SignupRequest("testuser@example.com", "password123");

        // When
        SignupResponse response = userService.signup(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("testuser@example.com", response.getEmail());
        assertEquals("User registered successfully. Please verify your email.", response.getMessage());
    }

    @Test
    void testSignupDuplicateEmail() {
        // Given
        SignupRequest request1 = new SignupRequest("duplicate@example.com", "password123");
        SignupRequest request2 = new SignupRequest("duplicate@example.com", "password456");

        // When
        userService.signup(request1);

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(request2);
        });
    }

    @Test
    void testPasswordIsEncrypted() {
        // Given
        SignupRequest request = new SignupRequest("encrypt@example.com", "plainpassword");

        // When
        SignupResponse response = userService.signup(request);
        User user = userRepository.findByEmail("encrypt@example.com").orElseThrow();

        // Then
        assertNotEquals("plainpassword", user.getPassword());
        assertTrue(user.getPassword().startsWith("$2a$")); // BCrypt hash prefix
    }

    @Test
    void testLoginSuccess() {
        // Given
        SignupRequest signupRequest = new SignupRequest("logintest@example.com", "mypassword");
        SignupResponse signupResponse = userService.signup(signupRequest);

        // Verify email before login
        userService.verifyEmail(signupResponse.getVerificationToken());

        // When
        User user = userService.login("logintest@example.com", "mypassword");

        // Then
        assertNotNull(user);
        assertEquals("logintest@example.com", user.getEmail());
    }

    @Test
    void testLoginInvalidEmail() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("nonexistent@example.com", "password");
        });
    }

    @Test
    void testLoginInvalidPassword() {
        // Given
        SignupRequest signupRequest = new SignupRequest("wrongpass@example.com", "correctpassword");
        userService.signup(signupRequest);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.login("wrongpass@example.com", "wrongpassword");
        });
    }

    @Test
    void testVerifyPassword() {
        // Given
        String plainPassword = "testpassword";
        SignupRequest request = new SignupRequest("verify@example.com", plainPassword);
        userService.signup(request);
        User user = userRepository.findByEmail("verify@example.com").orElseThrow();

        // When
        boolean isValid = userService.verifyPassword(plainPassword, user.getPassword());
        boolean isInvalid = userService.verifyPassword("wrongpassword", user.getPassword());

        // Then
        assertTrue(isValid);
        assertFalse(isInvalid);
    }
}
