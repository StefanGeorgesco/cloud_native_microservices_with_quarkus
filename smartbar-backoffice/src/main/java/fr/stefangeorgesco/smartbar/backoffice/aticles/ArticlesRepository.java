package fr.stefangeorgesco.smartbar.backoffice.aticles;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ArticlesRepository implements PanacheRepository<Article> {
}
