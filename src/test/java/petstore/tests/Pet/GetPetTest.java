package petstore.tests.Pet;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Pet;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.List;
import java.util.Random;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;
import static petstore.fixture.TestData.*;

public class GetPetTest extends BaseTest {

    @Test
    public void getPetByIdShouldReturnCreatedPet() {
        Pet pet = TestData.pet(DOG_NAME, DOG_STATUS).build();

        long petId = apiClient.post(Urls.PET, pet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class)
                .getId();

        Pet response = apiClient.get(Urls.PET_BY_ID, "petId", petId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class);

        assertAll("Retrieved pet matches created pet",
                () -> assertEquals(petId, response.getId()),
                () -> assertEquals(DOG_NAME, response.getName()),
                () -> assertEquals(DOG_STATUS, response.getStatus())
        );
    }

    @Test
    public void getPetByIdUnhappy() {
        long nonExistentId = -new Random().nextLong();

        apiClient.get(Urls.PET_BY_ID, "petId", nonExistentId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getPetByIdShouldMatchSchema() {
        Pet pet = TestData.pet(DOG_NAME, DOG_STATUS)
                .category(TestData.category())
                .tags(List.of(TestData.tag()))
                .photoUrls(List.of(PHOTO_URL))
                .build();

        long petId = apiClient.post(Urls.PET, pet)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Pet.class)
                .getId();

        apiClient.get(Urls.PET_BY_ID, "petId", petId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(matchesJsonSchemaInClasspath("schemas/pet-schema.json"));
    }
}