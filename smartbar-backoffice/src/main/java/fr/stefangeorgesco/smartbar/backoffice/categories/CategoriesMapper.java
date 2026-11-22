package fr.stefangeorgesco.smartbar.backoffice.categories;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiCategory;

public class CategoriesMapper {

    private CategoriesMapper() {
    }

    public static Category toEntity(ApiCategory apiCategory) {
        final Category category = new Category();
        category.setName(apiCategory.getName());
        category.setDescription(apiCategory.getDescription());
        return category;
    }

    public static ApiCategory toApi(Category category) {
        final ApiCategory apiCategory = new ApiCategory();
        apiCategory.setId(category.getId());
        apiCategory.setName(category.getName());
        apiCategory.setDescription(category.getDescription());
        return apiCategory;
    }

    public static void updateEntity(Category category, ApiCategory apiCategory) {
        category.setName(apiCategory.getName());
        category.setDescription(apiCategory.getDescription());
    }
}
