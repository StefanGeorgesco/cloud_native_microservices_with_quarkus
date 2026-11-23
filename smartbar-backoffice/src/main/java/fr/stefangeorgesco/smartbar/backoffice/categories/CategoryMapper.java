package fr.stefangeorgesco.smartbar.backoffice.categories;

import fr.stefangeorgesco.smartbar.backoffice.api.model.ApiCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.CDI)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    void mapToCategory(ApiCategory apiCategory, @MappingTarget Category category);

    ApiCategory mapToApiCategory(Category category);
}
