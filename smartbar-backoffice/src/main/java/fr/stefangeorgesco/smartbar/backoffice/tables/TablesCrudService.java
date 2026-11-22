package fr.stefangeorgesco.smartbar.backoffice.tables;

import fr.stefangeorgesco.smartbar.backoffice.CrudService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
@SuppressWarnings("unused")
public class TablesCrudService extends CrudService<Table> {

    // For CDI requirements
    public TablesCrudService() {
        super(null);
    }

    @Inject
    public TablesCrudService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Table> getEntityClass() {
        return Table.class;
    }
}