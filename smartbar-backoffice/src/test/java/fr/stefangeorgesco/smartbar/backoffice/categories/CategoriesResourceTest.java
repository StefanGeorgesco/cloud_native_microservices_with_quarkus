package fr.stefangeorgesco.smartbar.backoffice.categories;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiCategory;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class CategoriesResourceTest {

    private static final String DRINKS = "{\"name\":\"drinks\",\"description\":\"All drinks\"}";
    private static final String FOOD = "{\"name\":\"food\",\"description\":\"All food\"}";

    private String categoryId;

    // Data is set up through the REST API so that the same tests also run
    // in packaged mode (CategoriesResourceIT), where CDI injection is unavailable.
    @BeforeEach
    void setUp() {
        String location = given()
                .contentType("application/json")
                .body(DRINKS)
                .when().post("/categories")
                .then()
                .statusCode(201)
                .extract().header("Location");
        categoryId = location.substring(location.lastIndexOf('/') + 1);
    }

    @AfterEach
    void tearDown() {
        List<Object> ids = given()
                .when().get("/categories")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("id");
        ids.forEach(id -> given().when().delete("/categories/" + id));
    }

    @Test
    void categoriesCategoryIdDelete() {
        given()
                .when().delete("/categories/" + categoryId)
                .then()
                .statusCode(204);
        given()
                .when().get("/categories/" + categoryId)
                .then()
                .statusCode(404);
    }

    @Test
    void categoriesCategoryIdDeleteNotFound() {
        given()
                .when().delete("/categories/0")
                .then()
                .statusCode(404);
    }

    @Test
    void categoriesCategoryIdGet() {
        JsonPath jsonPath = given()
                .when().get("/categories/" + categoryId)
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(Long.parseLong(categoryId), jsonPath.getLong("id"));
        assertEquals("drinks", jsonPath.getString("name"));
        assertEquals("All drinks", jsonPath.getString("description"));
    }

    @Test
    void categoriesCategoryIdGetNotFound() {
        given()
                .when().get("/categories/0")
                .then()
                .statusCode(404);
    }

    @Test
    void categoriesCategoryIdPut() {
        given()
                .contentType("application/json")
                .body(FOOD)
                .when().put("/categories/" + categoryId)
                .then()
                .statusCode(200);
        ApiCategory updatedCategory = getApiCategory(categoryId);
        assertEquals("food", updatedCategory.getName());
        assertEquals("All food", updatedCategory.getDescription());
    }

    @Test
    void categoriesCategoryIdPutNotFound() {
        given()
                .contentType("application/json")
                .body(DRINKS)
                .when().put("/categories/0")
                .then()
                .statusCode(404);
    }

    @Test
    void categoriesGet() {
        JsonPath jsonPath = given()
                .when().get("/categories")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(1, jsonPath.getList("$").size());
        assertEquals("drinks", jsonPath.getString("[0].name"));
    }

    @Test
    void categoriesPost() {
        String location = given()
                .contentType("application/json")
                .body(FOOD)
                .when().post("/categories")
                .then()
                .statusCode(201)
                .extract().header("Location");
        String createdCategoryId = location.substring(location.lastIndexOf('/') + 1);
        ApiCategory createdCategory = getApiCategory(createdCategoryId);
        assertEquals("food", createdCategory.getName());
        assertEquals("All food", createdCategory.getDescription());
    }

    private ApiCategory getApiCategory(String categoryId) {
        return given()
                .when().get("/categories/" + categoryId)
                .then()
                .statusCode(200)
                .extract().as(ApiCategory.class);
    }
}
