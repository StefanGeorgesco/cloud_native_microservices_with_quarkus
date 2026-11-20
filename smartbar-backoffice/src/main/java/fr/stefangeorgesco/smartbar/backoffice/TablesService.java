package fr.stefangeorgesco.smartbar.backoffice;

import fr.stefangeorgesco.smartbar.backoffice.api.model.Table;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TablesService {

    public Table get() {
        return new Table().name("chile");
    }
}
