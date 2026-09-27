package petstore.tests.user;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.User;
import petstore.enums.Urls;
import petstore.fixture.TestData;

public class UpdateUserTest extends BaseTest {

    @Test
    public void updateUserShouldAcceptUpdateRequest() {
        User user = TestData.user().build();

        apiClient.post(Urls.USER, user)
                .then()
                .statusCode(HttpStatus.SC_OK);

        User updatedUser = TestData.user()
                .username(user.getUsername())
                .email("updated@example.com")
                .build();

        apiClient.put(Urls.USER_BY_USERNAME, "username", user.getUsername(), updatedUser)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}