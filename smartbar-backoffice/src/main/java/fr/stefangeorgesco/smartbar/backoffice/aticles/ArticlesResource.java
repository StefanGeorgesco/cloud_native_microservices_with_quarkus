package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.api.ArticlesApi;
import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiArticle;
import fr.stefangeorgesco.smartbar.backoffice.categories.CategoriesCrudService;
import fr.stefangeorgesco.smartbar.backoffice.categories.Category;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;

@Transactional
public class ArticlesResource implements ArticlesApi {

    private final ArticlesCrudService articlesCrudService;
    private final CategoriesCrudService categoriesCrudService;
    private final ArticleMapper articleMapper;

    @Inject
    public ArticlesResource(ArticlesCrudService articlesCrudService, CategoriesCrudService categoriesCrudService,
                            ArticleMapper articleMapper) {
        this.articlesCrudService = articlesCrudService;
        this.categoriesCrudService = categoriesCrudService;
        this.articleMapper = articleMapper;
    }


    @Override
    public Response articlesArticleIdDelete(Long articleId) {
        final Optional<Article> article = articlesCrudService.deleteById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @Override
    public Response articlesArticleIdGet(Long articleId) {
        final Optional<Article> article = articlesCrudService.getById(articleId);
        if (article.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((articleMapper.toApiArticle(article.get()))).build();
    }

    @Override
    public Response articlesArticleIdPut(Long articleId, ApiArticle apiArticle) {
        final Optional<Article> existingArticle = articlesCrudService.getById(articleId);
        if (existingArticle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = existingArticle.get();
        articleMapper.mapToArticle(apiArticle, article);
        articlesCrudService.update(article);
        return Response.ok().build();
    }

    @Override
    public Response articlesGet() {
        return Response.ok(articlesCrudService.getAll()
                        .stream()
                        .map(articleMapper::toApiArticle)
                        .toList())
                .build();
    }

    @Override
    public Response articlesPost(Long categoryId, ApiArticle apiArticle) {
        final Optional<Category> category = categoriesCrudService.getById(categoryId);
        if (category.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = new Article();
        articleMapper.mapToArticle(apiArticle, article);
        article.setCategory(category.get());
        final Article persitedArticle = articlesCrudService.persist(article);
        return Response.created(URI.create("/articles/" + persitedArticle.getId())).build();
    }
}
