package petstore.tests.user;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.User;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.UUID;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;
import static petstore.fixture.TestData.*;

public class GetUserTest extends BaseTest {

    @Test
    public void getUserByUsernameShouldReturnCreatedUser() {
        User user = TestData.user().build();

        apiClient.post(Urls.USER, user)
                .then()
                .statusCode(HttpStatus.SC_OK);

        User response = apiClient.get(Urls.USER_BY_USERNAME, "username", user.getUsername())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(User.class);

        assertAll("Retrieved user matches created user",
                () -> assertEquals(user.getUsername(), response.getUsername()),
                () -> assertEquals(FIRST_NAME, response.getFirstName()),
                () -> assertEquals(LAST_NAME, response.getLastName()),
                () -> assertEquals(EMAIL, response.getEmail()),
                () -> assertEquals(PHONE, response.getPhone())
        );
    }

    @Test
    public void getUserByUsernameUnhappy() {
        String nonExistentUsername = "nonexistent_" + UUID.randomUUID();

        apiClient.get(Urls.USER_BY_USERNAME, "username", nonExistentUsername)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getUserByUsernameShouldMatchSchema() {
        User user = TestData.user().build();

        apiClient.post(Urls.USER, user)
                .then()
                .statusCode(HttpStatus.SC_OK);

        apiClient.get(Urls.USER_BY_USERNAME, "username", user.getUsername())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }
}