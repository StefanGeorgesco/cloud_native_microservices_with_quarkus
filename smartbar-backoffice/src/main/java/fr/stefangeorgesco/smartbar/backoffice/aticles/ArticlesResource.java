package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.api.ArticlesApi;
import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiArticle;
import fr.stefangeorgesco.smartbar.backoffice.categories.CategoriesRepository;
import fr.stefangeorgesco.smartbar.backoffice.categories.Category;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Transactional
public class ArticlesResource implements ArticlesApi {

    private final ArticlesRepository articlesRepository;
    private final CategoriesRepository categoriesRepository;
    private final ArticleMapper articleMapper;

    @Inject
    public ArticlesResource(ArticlesRepository articlesRepository, CategoriesRepository categoriesRepository, ArticleMapper articleMapper) {
        this.articlesRepository = articlesRepository;
        this.categoriesRepository = categoriesRepository;
        this.articleMapper = articleMapper;
    }


    @Override
    public Response articlesArticleIdDelete(Long articleId) {
        final Optional<Article> article = articlesRepository.findByIdOptional(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        articlesRepository.delete(article.get());
        return Response.noContent().build();
    }

    @Override
    public Response articlesArticleIdGet(Long articleId) {
        final Optional<Article> article = articlesRepository.findByIdOptional(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((articleMapper.toApiArticle(article.get()))).build();
    }

    @Override
    public Response articlesArticleIdPut(Long articleId, ApiArticle apiArticle) {
        final Optional<Article> existingArticle = articlesRepository.findByIdOptional(articleId);
        if (existingArticle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = existingArticle.get();
        articleMapper.mapToArticle(apiArticle, article);
        return Response.ok().build();
    }

    @Override
    public Response articlesGet() {
        final List<Article> articles = articlesRepository.listAll();
        return Response.ok(articles
                        .stream()
                        .map(articleMapper::toApiArticle)
                        .toList())
                .build();
    }

    @Override
    public Response articlesPost(Long categoryId, ApiArticle apiArticle) {
        final Optional<Category> category = categoriesRepository.findByIdOptional(categoryId);
        if (category.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = new Article();
        articleMapper.mapToArticle(apiArticle, article);
        article.setCategory(category.get());
        articlesRepository.persist(article);
        return Response.created(URI.create("/articles/" + article.getId())).build();
    }
}
