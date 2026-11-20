package fr.stefangeorgesco.smartbar.backoffice.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class CategoriesResourceTest {

    @Test
    void categoriesCategoryIdDelete() {
        given()
                .when().delete("/categories/1")
                .then()
                .statusCode(200);
    }

    @Test
    void categoriesCategoryIdGet() {
        Response response = given()
                .when().get("/categories/1")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();
        assertEquals("drinks", jsonPath.getString("name"));
        assertEquals("All drinks", jsonPath.getString("description"));
    }

    @Test
    void categoriesCategoryIdPut() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"drinks\",\"description\":\"All drinks\"}")
                .when().put("/categories/1")
                .then()
                .statusCode(200);
    }

    @Test
    void categoriesGet() {
        Response response = given()
                .when().get("/categories")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();
        assertEquals("drinks", jsonPath.getString("[0].name"));
        assertEquals("All drinks", jsonPath.getString("[0].description"));
    }

    @Test
    void categoriesPost() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"drinks\",\"description\":\"All drinks\"}")
                .when().post("/categories")
                .then()
                .statusCode(201);
    }
}