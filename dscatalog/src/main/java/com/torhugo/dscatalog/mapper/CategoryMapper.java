package com.torhugo.dscatalog.mapper;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import com.torhugo.dscatalog.model.entities.CategoryModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryModel mapper(final CategoryDTO dto){
        final CategoryModel entity = new CategoryModel();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }
}
