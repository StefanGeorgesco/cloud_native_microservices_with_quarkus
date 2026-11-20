package fr.stefangeorgesco.smartbar.backoffice.api;

import fr.stefangeorgesco.smartbar.backoffice.CategoriesService;
import fr.stefangeorgesco.smartbar.backoffice.api.model.Category;
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
class CategoriesResourceTest {

    @InjectMock
    CategoriesService categoriesService;

    @BeforeEach
    void setUp() {
        when(categoriesService.get()).thenReturn(new Category().name("Mock category"));
    }

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
        assertEquals("Mock category", jsonPath.getString("name"));
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
        assertEquals("Mock category", jsonPath.getString("[0].name"));
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