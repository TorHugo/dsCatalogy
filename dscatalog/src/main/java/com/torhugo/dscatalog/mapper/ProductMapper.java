package com.torhugo.dscatalog.mapper;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.model.entities.ProductModel;
import com.torhugo.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductModel mapper(final ProductDTO dto){
        final ProductModel entity = new ProductModel();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());

        entity.getCategories().clear(); // limpando conjunto de categorias

        for (CategoryDTO categoryDto: dto.getLsCategories()) {
            CategoryModel category = categoryRepository.getOne(categoryDto.getId());
            entity.getCategories().add(category);
        }

        return entity;
    }
}
