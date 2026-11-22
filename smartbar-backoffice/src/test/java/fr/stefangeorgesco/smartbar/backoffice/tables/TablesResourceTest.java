package fr.stefangeorgesco.smartbar.backoffice.tables;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiTable;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TablesResourceTest {

    private static final String CHILE = "{\"name\":\"Chile\",\"seatCount\":4,\"active\":true}";

    private String tableId;

    // Data is set up through the REST API so that the same tests also run
    // in packaged mode (TablesResourceIT), where CDI injection is unavailable.
    @BeforeEach
    void setUp() {
        String location = given()
                .contentType("application/json")
                .body(CHILE)
                .when().post("/tables")
                .then()
                .statusCode(201)
                .extract().header("Location");
        tableId = location.substring(location.lastIndexOf('/') + 1);
    }

    @AfterEach
    void tearDown() {
        List<Object> ids = given()
                .when().get("/tables")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("id");
        ids.forEach(id -> given().when().delete("/tables/" + id));
    }

    @Test
    void tablesTableIdDelete() {
        given()
                .when().delete("/tables/" + tableId)
                .then()
                .statusCode(204);
        given()
                .when().get("/tables/" + tableId)
                .then()
                .statusCode(404);
    }

    @Test
    void tablesTableIdDeleteNotFound() {
        given()
                .when().delete("/tables/0")
                .then()
                .statusCode(404);
    }

    @Test
    void tablesTableIdGet() {
        JsonPath jsonPath = given()
                .when().get("/tables/" + tableId)
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(Long.parseLong(tableId), jsonPath.getLong("id"));
        assertEquals("Chile", jsonPath.getString("name"));
        assertEquals(4, jsonPath.getInt("seatCount"));
        assertTrue(jsonPath.getBoolean("active"));
    }

    @Test
    void tablesTableIdGetNotFound() {
        given()
                .when().get("/tables/0")
                .then()
                .statusCode(404);
    }

    @Test
    void tablesTableIdPut() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"Peru\",\"seatCount\":6,\"active\":false}")
                .when().put("/tables/" + tableId)
                .then()
                .statusCode(200);
        ApiTable updatedTable = getApiTable(tableId);
        assertEquals("Peru", updatedTable.getName());
        assertEquals(6, updatedTable.getSeatCount());
        assertFalse(updatedTable.getActive());
    }

    @Test
    void tablesTableIdPutNotFound() {
        given()
                .contentType("application/json")
                .body(CHILE)
                .when().put("/tables/0")
                .then()
                .statusCode(404);
    }

    @Test
    void tablesGet() {
        JsonPath jsonPath = given()
                .when().get("/tables")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(1, jsonPath.getList("$").size());
        assertEquals("Chile", jsonPath.getString("[0].name"));
    }

    @Test
    void tablesPost() {
        String location = given()
                .contentType("application/json")
                .body(CHILE)
                .when().post("/tables")
                .then()
                .statusCode(201)
                .extract().header("Location");
        String createdTableId = location.substring(location.lastIndexOf('/') + 1);
        ApiTable createdTable = getApiTable(createdTableId);
        assertEquals("Chile", createdTable.getName());
        assertEquals(4, createdTable.getSeatCount());
        assertTrue(createdTable.getActive());

    }

    private ApiTable getApiTable(String tableId) {
        return given()
                .when().get("/tables/" + tableId)
                .then()
                .statusCode(200)
                .extract().as(ApiTable.class);
    }
}
