package petstore.tests.store;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import petstore.base.BaseTest;
import petstore.dto.Order;
import petstore.enums.Urls;
import petstore.fixture.TestData;

import java.util.Random;

public class DeleteOrderTest extends BaseTest {

    @Test
    public void deleteOrderShouldRemoveOrderFromStore() {
        Order order = TestData.order().build();

        long orderId = apiClient.post(Urls.STORE_ORDER, order)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(Order.class)
                .getId();

        apiClient.delete(Urls.STORE_ORDER_BY_ID, "orderId", orderId)
                .then()
                .statusCode(HttpStatus.SC_OK);

        apiClient.get(Urls.STORE_ORDER_BY_ID, "orderId", orderId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deleteOrderUnhappy() {
        long nonExistentId = -new Random().nextLong();

        apiClient.delete(Urls.STORE_ORDER_BY_ID, "orderId", nonExistentId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}