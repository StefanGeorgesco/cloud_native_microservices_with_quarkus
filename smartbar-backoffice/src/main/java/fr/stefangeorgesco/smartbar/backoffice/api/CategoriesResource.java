package fr.stefangeorgesco.smartbar.backoffice.api;

import fr.stefangeorgesco.smartbar.backoffice.api.model.Category;
import io.smallrye.common.annotation.NonBlocking;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@NonBlocking
public class CategoriesResource implements CategoriesApi {

    private final Category category = new Category().name("drinks").description("All drinks");

    @Override
    public Response categoriesCategoryIdDelete(String categoryId) {
        return Response.ok().build();
    }

    @Override
    public Response categoriesCategoryIdGet(String categoryId) {
        return Response.ok().entity(category).build();
    }

    @Override
    public Response categoriesCategoryIdPut(String categoryId, Category category) {
        return Response.ok().build();
    }

    @Override
    public Response categoriesGet() {
        return Response.ok().entity(List.of(category)).build();
    }

    @Override
    public Response categoriesPost(Category category) {
        return Response.created(URI.create("todo")).build();
    }
}
