package petstore.tests.user;

import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.User;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest {

    @Test
    public void createUserShouldCreateUserSuccessfully() {
        User user = TestData.user().build();

        JsonPath response = apiClient.post(Urls.USER, user)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .jsonPath();

        assertAll("Create user response",
                () -> assertEquals(HttpStatus.SC_OK, response.getInt("code")),
                () -> assertNotNull(response.getString("message"))
        );
    }

    @Test
    public void createUserMalformedJson() {
        apiClient.post(Urls.USER, "{ not valid json ")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}