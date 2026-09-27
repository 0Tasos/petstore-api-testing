package petstore.tests.Pet;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Pet;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.Random;

import static petstore.fixture.TestData.*;

public class DeletePetTest extends BaseTest {

    @Test
    public void deletePetShouldRemovePetFromStore() {
        Pet pet = TestData.pet(DOG_NAME, DOG_STATUS).build();

        long petId = apiClient.post(Urls.PET, pet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class)
                .getId();

        apiClient.delete(Urls.PET_BY_ID, "petId", petId)
                .then()
                .statusCode(HttpStatus.SC_OK);

        apiClient.get(Urls.PET_BY_ID, "petId", petId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deletePetUnhappy() {
        long nonExistentId = -new Random().nextLong();

        apiClient.delete(Urls.PET_BY_ID, "petId", nonExistentId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}