package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.LoginRequest;
import org.acme.dto.LoginResponse;
import org.acme.entity.User;
import org.acme.service.UserService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    UserService userService;

    @POST
    public Response login(@Valid LoginRequest loginRequest) {
        try {
            // Authenticate user - validates if user exists in DB
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            // Generate JWT token for authenticated user
            String token = userService.generateToken(user);

            // Return success response with token
            LoginResponse response = new LoginResponse(
                    token,
                    user.getEmail(),
                    "Login successful");

            return Response.ok(response).build();

        } catch (IllegalArgumentException e) {
            // Check if it's an email verification error
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("email not verified")) {
                LoginResponse errorResponse = new LoginResponse(
                        null,
                        null,
                        e.getMessage());
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(errorResponse)
                        .build();
            }

            // User not found or invalid credentials
            LoginResponse errorResponse = new LoginResponse(
                    null,
                    null,
                    e.getMessage() != null ? e.getMessage()
                            : "Invalid credentials. Please signup if you don't have an account.");
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(errorResponse)
                    .build();
        } catch (Exception e) {
            // Server error
            LoginResponse errorResponse = new LoginResponse(
                    null,
                    null,
                    "An error occurred during login");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }
}
