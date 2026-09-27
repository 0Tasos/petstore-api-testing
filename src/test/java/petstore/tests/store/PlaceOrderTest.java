package petstore.tests.store;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Order;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void placeOrderShouldCreateOrderSuccessfully() {
        Order order = TestData.order().build();

        Order response = apiClient.post(Urls.STORE_ORDER, order)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Order.class);

        assertAll("Placed order matches",
                () -> assertTrue(response.getId() > 0),
                () -> assertEquals(order.getPetId(), response.getPetId()),
                () -> assertEquals(order.getQuantity(), response.getQuantity()),
                () -> assertEquals(order.getStatus(), response.getStatus()),
                () -> assertEquals(order.isComplete(), response.isComplete())
        );
    }
}