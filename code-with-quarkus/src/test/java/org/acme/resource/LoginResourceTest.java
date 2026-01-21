package org.acme.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
class LoginResourceTest {

        @Inject
        UserRepository userRepository;

        @BeforeEach
        @Transactional
        void setUp() {
                // Clean up test data before each test
                userRepository.deleteAll();

                // Create and verify a test user directly in the database
                User testUser = new User("testuser@example.com", BcryptUtil.bcryptHash("password123"));
                testUser.setEmailVerified(true); // Mark as verified so login can succeed
                userRepository.persist(testUser);
        }

        @Test
        void testLoginSuccess() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"testuser@example.com\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(200)
                                .body("email", is("testuser@example.com"))
                                .body("message", is("Login successful"));
        }

        @Test
        void testLoginInvalidEmail() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"nonexistent@example.com\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(401)
                                .body("message", containsString("Invalid"));
        }

        @Test
        void testLoginInvalidPassword() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"testuser@example.com\",\"password\":\"wrongpassword\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(401)
                                .body("message", containsString("Invalid"));
        }

        @Test
        void testLoginEmptyEmail() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(400);
        }

        @Test
        void testLoginEmptyPassword() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"testuser@example.com\",\"password\":\"\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(400);
        }

        @Test
        void testLoginInvalidEmailFormat() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"notanemail\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(400);
        }

        @Test
        void testLoginMissingEmail() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(400);
        }

        @Test
        void testLoginMissingPassword() {
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"testuser@example.com\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(400);
        }

        @Test
        void testLoginCaseSensitivity() {
                // Create user with lowercase email
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"lowercase@example.com\",\"password\":\"password123\"}")
                                .when()
                                .post("/signup");

                // Try login with uppercase - should fail if case-sensitive
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"LOWERCASE@EXAMPLE.COM\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(401);
        }

        @Test
        void testMultipleLoginsSameUser() {
                // First login
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"testuser@example.com\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(200);

                // Second login - should also succeed
                given()
                                .contentType(ContentType.JSON)
                                .body("{\"email\":\"testuser@example.com\",\"password\":\"password123\"}")
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(200);
        }
}
