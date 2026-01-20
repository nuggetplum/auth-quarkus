package org.acme.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "email-service")
@Path("/")
public interface EmailServiceClient {

    @POST
    @Path("/send-verification-email")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    EmailServiceResponse sendVerificationEmail(EmailServiceRequest request);

    class EmailServiceRequest {
        private String email;
        private String verificationToken;

        public EmailServiceRequest() {
        }

        public EmailServiceRequest(String email, String verificationToken) {
            this.email = email;
            this.verificationToken = verificationToken;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getVerificationToken() {
            return verificationToken;
        }

        public void setVerificationToken(String verificationToken) {
            this.verificationToken = verificationToken;
        }
    }

    class EmailServiceResponse {
        private boolean success;
        private String message;
        private String messageId;

        public EmailServiceResponse() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }
    }
}
