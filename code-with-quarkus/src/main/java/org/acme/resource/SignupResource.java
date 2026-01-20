package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.SignupRequest;
import org.acme.dto.SignupResponse;
import org.acme.service.UserService;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SignupResource {
    
    @Inject
    UserService userService;
    
    @POST
    public Response signup(@Valid SignupRequest request) {
        try {
            SignupResponse response = userService.signup(request);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("An error occurred during signup"))
                    .build();
        }
    }
    
    // Inner class for error responses
    public static class ErrorResponse {
        private String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
        
        public String getError() {
            return error;
        }
        
        public void setError(String error) {
            this.error = error;
        }
    }
}
