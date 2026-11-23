package fr.stefangeorgesco.smartbar.backoffice.aticles;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface ArticleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "picture", target = "pictureBase64")
    void mapToArticle(ApiArticle apiArticle, @MappingTarget Article article);

    @Mapping(source = "pictureBase64", target = "picture")
    ApiArticle toApiArticle(Article article);
}
