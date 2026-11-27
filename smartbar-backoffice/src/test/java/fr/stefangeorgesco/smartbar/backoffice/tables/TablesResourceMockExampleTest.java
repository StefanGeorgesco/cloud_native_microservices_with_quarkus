package fr.stefangeorgesco.smartbar.backoffice.tables;

import fr.stefangeorgesco.smartbar.backoffice.categories.CategoriesRepository;
import fr.stefangeorgesco.smartbar.backoffice.categories.Category;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TablesResourceMockExampleTest {

    @InjectMock
    @SuppressWarnings("unused")
    private CategoriesRepository categoriesRepository;

    @BeforeEach
    void setUp() {
        final Category category = new Category();
        category.setName("Mock");
        Mockito.when(categoriesRepository.listAll()).thenReturn(List.of(category));
    }

    @Test
    void getsListOfCategories() {
        final Response response = given()
                .when().get("/categories")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();

        assertEquals("Mock", jsonPath.getString("[0].name"));
    }
}
