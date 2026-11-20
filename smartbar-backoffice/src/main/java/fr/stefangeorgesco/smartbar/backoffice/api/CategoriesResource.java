package fr.stefangeorgesco.smartbar.backoffice.api;

import fr.stefangeorgesco.smartbar.backoffice.CategoriesService;
import fr.stefangeorgesco.smartbar.backoffice.api.model.Category;
import io.smallrye.common.annotation.NonBlocking;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@NonBlocking
public class CategoriesResource implements CategoriesApi {

    private final CategoriesService categoriesService;

    public CategoriesResource(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @Override
    public Response categoriesCategoryIdDelete(String categoryId) {
        return Response.ok().build();
    }

    @Override
    public Response categoriesCategoryIdGet(String categoryId) {
        return Response.ok().entity(categoriesService.get()).build();
    }

    @Override
    public Response categoriesCategoryIdPut(String categoryId, Category category) {
        return Response.ok().build();
    }

    @Override
    public Response categoriesGet() {
        return Response.ok().entity(List.of(categoriesService.get())).build();
    }

    @Override
    public Response categoriesPost(Category category) {
        return Response.created(URI.create("todo")).build();
    }
}
