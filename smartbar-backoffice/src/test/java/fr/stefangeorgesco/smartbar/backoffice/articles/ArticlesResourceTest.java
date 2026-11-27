package fr.stefangeorgesco.smartbar.backoffice.articles;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiArticle;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ArticlesResourceTest {

    private static final String CHARDONNAY =
            "{\"name\":\"Chardonnay\",\"price\":5.5,\"description\":\"White wine\",\"picture\":\"chardonnay.png\"}";

    private String articleId;
    private String categoryId;

    // Data is set up through the REST API so that the same tests also run
    // in packaged mode (ArticlesResourceIT), where CDI injection is unavailable.
    @BeforeEach
    void setUp() {
        categoryId = createCategory("drinks");
        String location = given()
                .contentType("application/json")
                .header("x-category-id", categoryId)
                .body(CHARDONNAY)
                .when().post("/articles")
                .then()
                .statusCode(201)
                .extract().header("Location");
        articleId = location.substring(location.lastIndexOf('/') + 1);
    }

    @AfterEach
    void tearDown() {
        deleteArticle(articleId);
        deleteCategory(categoryId);
    }

    @Test
    void articlesArticleIdDelete() {
        deleteArticle(articleId)
                .then()
                .statusCode(204);
        given()
                .when().get("/articles/" + articleId)
                .then()
                .statusCode(404);
    }

    @Test
    void articlesArticleIdDeleteNotFound() {
        given()
                .when().delete("/articles/0")
                .then()
                .statusCode(404);
    }

    @Test
    void articlesArticleIdGet() {
        JsonPath jsonPath = given()
                .when().get("/articles/" + articleId)
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(Long.parseLong(articleId), jsonPath.getLong("id"));
        assertEquals("Chardonnay", jsonPath.getString("name"));
        assertEquals(5.5, jsonPath.getDouble("price"));
        assertEquals("White wine", jsonPath.getString("description"));
        assertEquals("chardonnay.png", jsonPath.getString("picture"));
    }

    @Test
    void articlesArticleIdGetNotFound() {
        given()
                .when().get("/articles/0")
                .then()
                .statusCode(404);
    }

    @Test
    void articlesArticleIdPut() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"Merlot\",\"price\":6.5,\"description\":\"Red wine\",\"picture\":\"merlot.png\"}")
                .when().put("/articles/" + articleId)
                .then()
                .statusCode(200);
        ApiArticle updatedArticle = getApiArticle(articleId);
        assertEquals("Merlot", updatedArticle.getName());
        assertEquals(new BigDecimal("6.50"), updatedArticle.getPrice());
        assertEquals("Red wine", updatedArticle.getDescription());
        assertEquals("merlot.png", updatedArticle.getPicture());
    }

    @Test
    void articlesArticleIdPutNotFound() {
        given()
                .contentType("application/json")
                .body(CHARDONNAY)
                .when().put("/articles/0")
                .then()
                .statusCode(404);
    }

    @Test
    void articlesGet() {
        JsonPath jsonPath = given()
                .when().get("/articles")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(11, jsonPath.getList("$").size());
    }

    @Test
    void articlesPost() {
        String createdCategoryId = createCategory("other");
        String location = given()
                .contentType("application/json")
                .header("x-category-id", createdCategoryId)
                .body(CHARDONNAY)
                .when().post("/articles")
                .then()
                .statusCode(201)
                .extract().header("Location");
        String createdArticleId = location.substring(location.lastIndexOf('/') + 1);
        ApiArticle createdArticle = getApiArticle(createdArticleId);
        assertEquals("Chardonnay", createdArticle.getName());
        assertEquals(new BigDecimal("5.50"), createdArticle.getPrice());
        assertEquals("White wine", createdArticle.getDescription());
        assertEquals("chardonnay.png", createdArticle.getPicture());
        deleteArticle(createdArticleId);
        deleteCategory(createdCategoryId);
    }

    @Test
    void articlesByCategoryIdGet() {
        JsonPath jsonPath = given()
                .when().get("/articles/category/1000")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertEquals(2, jsonPath.getList("$").size());
        assertEquals("Iced coffee", jsonPath.getString("[0].name"));
    }

    private String createCategory(String name) {
        String location = given()
                .contentType("application/json")
                .body("{\"name\":\"%s\",\"description\":\"%s\"}".formatted(name, name))
                .when().post("/categories")
                .then()
                .statusCode(201)
                .extract().header("Location");
        return location.substring(location.lastIndexOf('/') + 1);
    }

    private void deleteCategory(String categoryId) {
        given().when().delete("/categories/" + categoryId);
    }

    private ApiArticle getApiArticle(String articleId) {
        return given()
                .when().get("/articles/" + articleId)
                .then()
                .statusCode(200)
                .extract().as(ApiArticle.class);
    }

    private Response deleteArticle(String articleId) {
        return given().when().delete("/articles/" + articleId);
    }
}
