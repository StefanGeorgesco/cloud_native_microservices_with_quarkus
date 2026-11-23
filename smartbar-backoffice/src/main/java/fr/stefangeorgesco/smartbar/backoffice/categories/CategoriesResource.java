package fr.stefangeorgesco.smartbar.backoffice.categories;

import fr.stefangeorgesco.smartbar.backoffice.api.CategoriesApi;
import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiCategory;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;

@Transactional
public class CategoriesResource implements CategoriesApi {

    private final CategoriesCrudService categoriesCrudService;
    private final CategoryMapper categoryMapper;

    public CategoriesResource(CategoriesCrudService categoriesCrudService, CategoryMapper categoryMapper) {
        this.categoriesCrudService = categoriesCrudService;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public Response categoriesCategoryIdDelete(Long categoryId) {
        final Optional<Category> category = categoriesCrudService.deleteById(categoryId);
        if (category.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @Override
    public Response categoriesCategoryIdGet(Long categoryId) {
        final Optional<Category> category = categoriesCrudService.getById(categoryId);
        if (category.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((categoryMapper.mapToApiCategory(category.get()))).build();
    }

    @Override
    public Response categoriesCategoryIdPut(Long categoryId, ApiCategory apiCategory) {
        final Optional<Category> existingCategory = categoriesCrudService.getById(categoryId);
        if (existingCategory.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Category category = existingCategory.get();
        categoryMapper.mapToCategory(apiCategory, category);
        categoriesCrudService.update(category);
        return Response.ok().build();
    }

    @Override
    public Response categoriesGet() {
        return Response.ok(categoriesCrudService.getAll()
                        .stream()
                        .map(categoryMapper::mapToApiCategory)
                        .toList())
                .build();
    }

    @Override
    public Response categoriesPost(ApiCategory apiCategory) {
        final Category category = new Category();
        categoryMapper.mapToCategory(apiCategory, category);
        final Category persitedCategory = categoriesCrudService.persist(category);
        return Response.created(URI.create("/categories/" + persitedCategory.getId())).build();
    }
}
