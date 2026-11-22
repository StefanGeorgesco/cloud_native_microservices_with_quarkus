package fr.stefangeorgesco.smartbar.backoffice.categories;

import fr.stefangeorgesco.smartbar.backoffice.CrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
@SuppressWarnings("unused")
public class CategoriesCrudService extends CrudService<Category> {

    // For CDI requirements
    public CategoriesCrudService() {
        super(null);
    }

    @Inject
    public CategoriesCrudService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}
