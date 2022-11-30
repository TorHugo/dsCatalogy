package com.torhugo.dscatalog.mapper;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import com.torhugo.dscatalog.model.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category mapper(final CategoryDTO dto){
        final Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }
}
