# Petstore API Test Suite

Integration tests for the Swagger Petstore API, written in Java 21 with Maven, JUnit 5, RestAssured, AssertJ, and Jackson.

Swagger UI: https://petstore.swagger.io/

## Run

    mvn test

Run a single class:

    mvn test -Dtest=CreatePetTest

## Structure
    
    src/main/java/petstore/
        dto/    request/response models

    src/test/java/petstore/
        base/      shared test setup (BaseTest)
        client/    generic HTTP client (ApiClient)
        enums/     API endpoints (Urls)
        fixture/   test data builders (TestData)
        tests/     JUnit tests, grouped by API resource

    src/test/resources/schemas/
        JSON schemas for response validation

## Coverage

Pet:
- create, get, update, delete
- find by status
- schema validation

Store:
- inventory
- place order, get order, delete order
- schema validation

User:
- create, get, update, delete
- schema validation

Each test class is focused on a single endpoint. Requests use DTOs instead of raw JSON strings.