package fr.stefangeorgesco.smartbar.backoffice;

import fr.stefangeorgesco.smartbar.backoffice.api.model.Article;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArticlesService {

    public Article get() {
        return new Article().name("Chardonnay");
    }
}
