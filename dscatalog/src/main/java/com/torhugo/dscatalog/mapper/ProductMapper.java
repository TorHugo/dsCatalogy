package com.torhugo.dscatalog.mapper;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.Category;
import com.torhugo.dscatalog.model.entities.Product;
import com.torhugo.dscatalog.repository.CategoryRepository;
import org.dom4j.dom.DOMNodeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public Product mapper(final ProductDTO dto){
        final Product entity = new Product();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());

        entity.getCategories().clear(); // limpando conjunto de categorias

        for (CategoryDTO categoryDto: dto.getLsCategories()) {
            Category category = categoryRepository.getOne(categoryDto.getId());
            entity.getCategories().add(category);
        }

        return entity;
    }
}
