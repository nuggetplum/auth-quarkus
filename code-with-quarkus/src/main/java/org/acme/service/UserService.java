package org.acme.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.SignupRequest;
import org.acme.dto.SignupResponse;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.acme.client.EmailServiceClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    @RestClient
    EmailServiceClient emailServiceClient;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
        }

        // Encrypt password using BCrypt
        String encryptedPassword = BcryptUtil.bcryptHash(request.getPassword());

        // Create and persist user
        User user = new User(request.getEmail(), encryptedPassword);

        // Generate verification token
        String verificationToken = generateVerificationToken();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24)); // Token valid for 24 hours
        user.setEmailVerified(false);

        userRepository.persist(user);

        // Send verification email asynchronously
        try {
            EmailServiceClient.EmailServiceRequest emailRequest = new EmailServiceClient.EmailServiceRequest(
                    user.getEmail(), verificationToken);
            emailServiceClient.sendVerificationEmail(emailRequest);
        } catch (Exception e) {
            // Log error but don't fail signup if email service is down
            System.err.println("Failed to send verification email: " + e.getMessage());
        }

        return new SignupResponse(
                user.getId(),
                user.getEmail(),
                "User registered successfully. Please verify your email.",
                verificationToken);
    }

    @Transactional
    public boolean verifyEmail(String token) {
        User user = userRepository.find("verificationToken", token).firstResultOptional()
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        // Check if token has expired
        if (user.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Verification token has expired");
        }

        // Check if already verified
        if (user.isEmailVerified()) {
            throw new IllegalArgumentException("Email already verified");
        }

        // Mark email as verified
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);

        return true;
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void resendVerificationEmail(String email) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("No account found with this email. Please sign up first."));

        // Check if already verified
        if (user.isEmailVerified()) {
            throw new IllegalArgumentException("Email is already verified. You can login now.");
        }

        // Generate new verification token
        String newToken = generateVerificationToken();
        user.setVerificationToken(newToken);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusHours(24));

        // Send verification email
        try {
            EmailServiceClient.EmailServiceRequest emailRequest = new EmailServiceClient.EmailServiceRequest(
                    user.getEmail(), newToken);
            emailServiceClient.sendVerificationEmail(emailRequest);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            throw new RuntimeException("Failed to send verification email. Please try again later.");
        }
    }

    public User login(String email, String password) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Verify password
        if (!verifyPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Check if email is verified
        if (!user.isEmailVerified()) {
            throw new IllegalArgumentException(
                    "Email not verified. Please check your email and verify your account before logging in.");
        }

        return user;
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BcryptUtil.matches(plainPassword, hashedPassword);
    }
}
