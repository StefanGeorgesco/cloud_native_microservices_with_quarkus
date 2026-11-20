package fr.stefangeorgesco.smartbar.backoffice.api;

import fr.stefangeorgesco.smartbar.backoffice.TablesService;
import fr.stefangeorgesco.smartbar.backoffice.api.model.Table;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class TablesResourceTest {

    @InjectMock
    TablesService tablesService;

    @BeforeEach
    void setUp() {
        when(tablesService.get()).thenReturn(new Table().name("Mock name"));
    }

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
        assertEquals("Mock name", jsonPath.getString("name"));
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
        assertEquals("Mock name", jsonPath.getString("[0].name"));
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
