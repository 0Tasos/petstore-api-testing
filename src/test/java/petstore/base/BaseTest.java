package petstore.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import petstore.client.ApiClient;

public abstract class BaseTest {

    protected static RequestSpecification spec;
    protected static ApiClient apiClient;

    @BeforeAll
    public static void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io")
                .setBasePath("/v2")
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        apiClient = new ApiClient(spec);
    }
}