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

    @Inject
    public ArticlesResource(ArticlesCrudService articlesCrudService, CategoriesCrudService categoriesCrudService) {
        this.articlesCrudService = articlesCrudService;
        this.categoriesCrudService = categoriesCrudService;
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
        return Response.ok((ArticlesMapper.toApi(article.get()))).build();
    }

    @Override
    public Response articlesArticleIdPut(Long articleId, ApiArticle apiArticle) {
        final Optional<Article> existingArticle = articlesCrudService.getById(articleId);
        if (existingArticle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = existingArticle.get();
        ArticlesMapper.updateEntity(article, apiArticle);
        articlesCrudService.update(article);
        return Response.ok().build();
    }

    @Override
    public Response articlesGet() {
        return Response.ok(articlesCrudService.getAll()
                        .stream()
                        .map(ArticlesMapper::toApi)
                        .toList())
                .build();
    }

    @Override
    public Response articlesPost(Long categoryId,ApiArticle apiArticle) {
        final Optional<Category> category = categoriesCrudService.getById(categoryId);
        if (category.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Article article = ArticlesMapper.toEntity(apiArticle);
        article.setCategory(category.get());
        final Article persitedArticle = articlesCrudService.persist(article);
        return Response.created(URI.create("/articles/" + persitedArticle.getId())).build();
    }
}
