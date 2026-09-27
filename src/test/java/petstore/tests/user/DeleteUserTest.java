package petstore.tests.user;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.User;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.UUID;

public class DeleteUserTest extends BaseTest {

    @Test
    public void deleteUserShouldRemoveUserFromStore() {
        User user = TestData.user().build();

        apiClient.post(Urls.USER, user)
                .then()
                .statusCode(HttpStatus.SC_OK);

        apiClient.delete(Urls.USER_BY_USERNAME, "username", user.getUsername())
                .then()
                .statusCode(HttpStatus.SC_OK);

        apiClient.get(Urls.USER_BY_USERNAME, "username", user.getUsername())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deleteUserUnhappy() {
        String nonExistentUsername = "nonexistent_" + UUID.randomUUID();

        apiClient.delete(Urls.USER_BY_USERNAME, "username", nonExistentUsername)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}