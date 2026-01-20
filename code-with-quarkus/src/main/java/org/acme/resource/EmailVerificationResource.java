package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.VerifyEmailRequest;
import org.acme.dto.VerifyEmailResponse;
import org.acme.dto.ResendVerificationRequest;
import org.acme.service.UserService;

@Path("/verify-email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailVerificationResource {

    @Inject
    UserService userService;

    @POST
    public Response verifyEmail(@Valid VerifyEmailRequest request) {
        try {
            boolean verified = userService.verifyEmail(request.getToken());

            VerifyEmailResponse response = new VerifyEmailResponse(
                    verified,
                    "Email verified successfully");

            return Response.ok(response).build();

        } catch (IllegalArgumentException e) {
            VerifyEmailResponse errorResponse = new VerifyEmailResponse(
                    false,
                    e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        } catch (Exception e) {
            VerifyEmailResponse errorResponse = new VerifyEmailResponse(
                    false,
                    "An error occurred during email verification");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    @GET
    @Path("/{token}")
    public Response verifyEmailByUrl(@PathParam("token") String token) {
        try {
            boolean verified = userService.verifyEmail(token);

            VerifyEmailResponse response = new VerifyEmailResponse(
                    verified,
                    "Email verified successfully");

            return Response.ok(response).build();

        } catch (IllegalArgumentException e) {
            VerifyEmailResponse errorResponse = new VerifyEmailResponse(
                    false,
                    e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        } catch (Exception e) {
            VerifyEmailResponse errorResponse = new VerifyEmailResponse(
                    false,
                    "An error occurred during email verification");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }

    @POST
    @Path("/resend")
    public Response resendVerificationEmail(@Valid ResendVerificationRequest request) {
        try {
            userService.resendVerificationEmail(request.getEmail());

            VerifyEmailResponse response = new VerifyEmailResponse(
                    true,
                    "Verification email sent successfully! Please check your inbox.");

            return Response.ok(response).build();

        } catch (IllegalArgumentException e) {
            VerifyEmailResponse errorResponse = new VerifyEmailResponse(
                    false,
                    e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        } catch (Exception e) {
            VerifyEmailResponse errorResponse = new VerifyEmailResponse(
                    false,
                    "An error occurred while sending verification email");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }
}
