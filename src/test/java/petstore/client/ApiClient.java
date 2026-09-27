package petstore.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import petstore.enums.Urls;

import static io.restassured.RestAssured.given;

@AllArgsConstructor
public class ApiClient {
    private final RequestSpecification spec;

    public Response get(Urls url) {
        return request().get(url.getPath());
    }

    public Response get(Urls url, String pathParam, Object value) {
        return request().pathParam(pathParam, value).get(url.getPath());
    }

    public Response getWithQueryParam(Urls url, String queryParam, Object value) {
        return request().queryParam(queryParam, value).get(url.getPath());
    }

    public Response post(Urls url, Object body) {
        return request().body(body).post(url.getPath());
    }

    public Response put(Urls url, Object body) {
        return request().body(body).put(url.getPath());
    }

    public Response put(Urls url, String pathParam, Object value, Object body) {
        return request().pathParam(pathParam, value).body(body).put(url.getPath());
    }

    public Response delete(Urls url, String pathParam, Object value) {
        return request().pathParam(pathParam, value).delete(url.getPath());
    }

    private RequestSpecification request() {
        return given().spec(spec);
    }
}