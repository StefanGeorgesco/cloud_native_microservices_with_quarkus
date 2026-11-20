package fr.stefangeorgesco.smartbar.backoffice.api;

import fr.stefangeorgesco.smartbar.backoffice.ArticlesService;
import fr.stefangeorgesco.smartbar.backoffice.api.model.Article;
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
class ArticlesResourceTest {

    @InjectMock
    ArticlesService articlesService;

    @BeforeEach
    void setUp() {
        when(articlesService.get()).thenReturn(new Article().name("Mock article"));
    }

    @Test
    void articlesArticleIdDelete() {
        given()
                .when().delete("/articles/1")
                .then()
                .statusCode(200);
    }

    @Test
    void articlesArticleIdGet() {
        Response response = given()
                .when().get("/articles/1")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();
        assertEquals("Mock article", jsonPath.getString("name"));
    }

    @Test
    void articlesArticleIdPut() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"Chardonnay\",\"price\":5.5,\"description\":\"White wine\",\"picture\":\"chardonnay.png\"}")
                .when().put("/articles/1")
                .then()
                .statusCode(200);
    }

    @Test
    void articlesGet() {
        Response response = given()
                .when().get("/articles")
                .then()
                .statusCode(200)
                .extract().response();
        final JsonPath jsonPath = response.jsonPath();
        assertEquals("Mock article", jsonPath.getString("[0].name"));
    }

    @Test
    void articlesPost() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"Chardonnay\",\"price\":5.5,\"description\":\"White wine\",\"picture\":\"chardonnay.png\"}")
                .when().post("/articles")
                .then()
                .statusCode(201);
    }
}
