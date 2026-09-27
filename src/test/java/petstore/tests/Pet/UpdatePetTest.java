package petstore.tests.Pet;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Pet;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import static org.junit.jupiter.api.Assertions.*;
import static petstore.fixture.TestData.*;

public class UpdatePetTest extends BaseTest {

    @Test
    public void updatePetShouldUpdatePetSuccessfully() {
        Pet pet = TestData.pet(DOG_NAME, DOG_STATUS).build();

        long petId = apiClient.post(Urls.PET, pet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class)
                .getId();

        Pet updatedPet = TestData.pet("name after", "sold")
                .id(petId)
                .build();

        Pet response = apiClient.put(Urls.PET, updatedPet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class);

        assertAll("Updated pet matches",
                () -> assertEquals(petId, response.getId()),
                () -> assertEquals("name after", response.getName()),
                () -> assertEquals("sold", response.getStatus())
        );
    }
}