package petstore.tests.Pet;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Pet;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static petstore.fixture.TestData.*;

public class FindPetByStatusTest extends BaseTest {

    @Test
    public void findPetByStatusShouldReturnCreatedPet() {
        Pet pet = TestData.pet(DOG_NAME, DOG_STATUS).build();

        long petId = apiClient.post(Urls.PET, pet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class)
                .getId();

        Pet[] pets = apiClient.getWithQueryParam(Urls.PET_FIND_BY_STATUS, "status", DOG_STATUS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet[].class);

        assertAll("Find pet by status",
                () -> assertNotNull(pets),
                () -> assertTrue(pets.length > 0),
                () -> assertTrue(Arrays.stream(pets).anyMatch(p -> p.getId() == petId))
        );
    }

    @Test
    public void findPetByStatusUnhappy() {
        Pet[] pets = apiClient.getWithQueryParam(Urls.PET_FIND_BY_STATUS, "status", "pending")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet[].class);

        assertNotNull(pets);
    }
}