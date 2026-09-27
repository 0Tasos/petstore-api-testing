package petstore.tests.store;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Order;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.Random;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

public class GetOrderTest extends BaseTest {

    @Test
    public void getOrderByIdShouldReturnPlacedOrder() {
        Order order = TestData.order().build();

        long orderId = apiClient.post(Urls.STORE_ORDER, order)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Order.class)
                .getId();

        Order response = apiClient.get(Urls.STORE_ORDER_BY_ID, "orderId", orderId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Order.class);

        assertAll("Retrieved order matches placed order",
                () -> assertEquals(orderId, response.getId()),
                () -> assertEquals(order.getPetId(), response.getPetId()),
                () -> assertEquals(order.getQuantity(), response.getQuantity()),
                () -> assertEquals(order.getStatus(), response.getStatus()),
                () -> assertEquals(order.isComplete(), response.isComplete())
        );
    }

    @Test
    public void getOrderByIdUnhappy() {
        long nonExistentId = -new Random().nextLong();

        apiClient.get(Urls.STORE_ORDER_BY_ID, "orderId", nonExistentId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getOrderByIdShouldMatchSchema() {
        Order order = TestData.order().build();

        long orderId = apiClient.post(Urls.STORE_ORDER, order)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Order.class)
                .getId();

        apiClient.get(Urls.STORE_ORDER_BY_ID, "orderId", orderId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(matchesJsonSchemaInClasspath("schemas/order-schema.json"));
    }
}