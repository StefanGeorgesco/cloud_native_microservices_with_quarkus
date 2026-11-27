package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.categories.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ArticlesRepository implements PanacheRepository<Article> {

    public List<Article> listByCategory(Category category) {
        // or find("category", Sort.by("price", Sort.Direction.Descending), category)
        // if you don't have a named query on the entity
        return find("#Article.byCategory", category)
                .page(Page.ofSize(2))
                .list();
    }
}
