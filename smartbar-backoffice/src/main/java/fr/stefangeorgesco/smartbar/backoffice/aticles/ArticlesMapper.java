package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiArticle;

public class ArticlesMapper {

    private ArticlesMapper() {
    }

    public static Article toEntity(ApiArticle apiArticle) {
        final Article article = new Article();
        article.setName(apiArticle.getName());
        article.setPrice(apiArticle.getPrice());
        article.setDescription(apiArticle.getDescription());
        article.setPicture(apiArticle.getPicture());
        return article;
    }

    public static ApiArticle toApi(Article article) {
        final ApiArticle apiArticle = new ApiArticle();
        apiArticle.setId(article.getId());
        apiArticle.setName(article.getName());
        apiArticle.setPrice(article.getPrice());
        apiArticle.setDescription(article.getDescription());
        apiArticle.setPicture(article.getPicture());
        return apiArticle;
    }

    public static void updateEntity(Article article, ApiArticle apiArticle) {
        article.setName(apiArticle.getName());
        article.setPrice(apiArticle.getPrice());
        article.setDescription(apiArticle.getDescription());
        article.setPicture(apiArticle.getPicture());
    }
}
