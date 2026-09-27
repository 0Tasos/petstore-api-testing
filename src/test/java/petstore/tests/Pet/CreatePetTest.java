package petstore.tests.Pet;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Pet;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static petstore.fixture.TestData.*;

public class CreatePetTest extends BaseTest {

    @Test
    public void createPetShouldCreatePetWithFullData() {
        Pet pet = TestData.pet(DOG_NAME, DOG_STATUS)
                .category(TestData.category())
                .tags(List.of(TestData.tag()))
                .photoUrls(List.of(PHOTO_URL))
                .build();

        Pet response = apiClient.post(Urls.PET, pet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class);

        assertAll("Created pet with full data",
                () -> assertEquals(DOG_NAME, response.getName()),
                () -> assertEquals(DOG_STATUS, response.getStatus()),
                () -> assertEquals(CATEGORY_ID, response.getCategory().getId()),
                () -> assertEquals(CATEGORY_NAME, response.getCategory().getName()),
                () -> assertEquals(TAG_NAME, response.getTags().getFirst().getName()),
                () -> assertIterableEquals(List.of(TestData.PHOTO_URL), response.getPhotoUrls())
        );
    }
}