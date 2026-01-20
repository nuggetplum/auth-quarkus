package org.acme.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
class SignupResourceTest {

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
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"newuser@example.com\",\"password\":\"password123\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(201)
                .body("email", is("newuser@example.com"))
                .body("message", is("User registered successfully"))
                .body("id", notNullValue())
                .body("id", greaterThan(0));
    }

    @Test
    void testSignupDuplicateEmail() {
        // First signup
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"duplicate@example.com\",\"password\":\"password123\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(201);

        // Second signup with same email
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"duplicate@example.com\",\"password\":\"password456\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(409)
                .body("error", containsString("already exists"));
    }

    @Test
    void testSignupInvalidEmail() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"invalidemail\",\"password\":\"password123\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testSignupEmptyEmail() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"\",\"password\":\"password123\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testSignupEmptyPassword() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"test@example.com\",\"password\":\"\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testSignupShortPassword() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"test@example.com\",\"password\":\"12345\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testSignupMissingEmail() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"password\":\"password123\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testSignupMissingPassword() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"test@example.com\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    void testSignupWithSpecialCharactersInPassword() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"special@example.com\",\"password\":\"P@ssw0rd!#$%\"}")
                .when()
                .post("/signup")
                .then()
                .statusCode(201)
                .body("email", is("special@example.com"))
                .body("message", is("User registered successfully"));
    }
}
