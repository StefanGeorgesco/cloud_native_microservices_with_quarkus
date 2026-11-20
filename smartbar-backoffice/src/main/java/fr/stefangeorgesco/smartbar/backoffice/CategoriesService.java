package fr.stefangeorgesco.smartbar.backoffice;

import fr.stefangeorgesco.smartbar.backoffice.api.model.Category;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriesService {

    public Category get() {
        return new Category().name("drinks").description("All drinks");
    }
}
