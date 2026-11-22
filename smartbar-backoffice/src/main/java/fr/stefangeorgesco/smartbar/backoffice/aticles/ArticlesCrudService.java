package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.CrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
@SuppressWarnings("unused")
public class ArticlesCrudService extends CrudService<Article> {

    // For CDI requirements
    public ArticlesCrudService() {
        super(null);
    }

    @Inject
    public ArticlesCrudService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Article> getEntityClass() {
        return Article.class;
    }
}
