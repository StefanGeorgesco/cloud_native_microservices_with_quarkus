package fr.stefangeorgesco.smartbar.backoffice.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class TablesResourceTest {

    @Test
    void tablesTableIdDelete() {
        given()
                .when().delete("/tables/1")
                .then()
                .statusCode(200);
    }

    @Test
    void tablesTableIdGet() {
        Response response = given()
                .when().get("/tables/1")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();
        assertEquals("chile", jsonPath.getString("name"));
    }

    @Test
    void tablesTableIdPut() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"chile\",\"seatCount\":4,\"active\":true}")
                .when().put("/tables/1")
                .then()
                .statusCode(200);
    }

    @Test
    void tablesGet() {
        Response response = given()
                .when().get("/tables")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();
        assertEquals("chile", jsonPath.getString("[0].name"));
    }

    @Test
    void tablesPost() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"chile\",\"seatCount\":4,\"active\":true}")
                .when().post("/tables")
                .then()
                .statusCode(201);
    }
}
