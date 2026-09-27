package petstore.tests.store;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.enums.Urls;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GetInventoryTest extends BaseTest {

    @Test
    public void getInventoryShouldReturnValidCounts() {
        Map<String, Integer> inventory = apiClient.get(Urls.STORE_INVENTORY)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Map.class);

        assertAll("Inventory counts are valid",
                () -> assertNotNull(inventory),
                () -> assertTrue(inventory.getOrDefault("available", 0) >= 0),
                () -> assertTrue(inventory.getOrDefault("pending", 0) >= 0),
                () -> assertTrue(inventory.getOrDefault("sold", 0) >= 0)
        );
    }
}