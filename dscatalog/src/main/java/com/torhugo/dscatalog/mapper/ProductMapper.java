package com.torhugo.dscatalog.mapper;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.model.entities.ProductModel;
import com.torhugo.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductModel mapper(final ProductDTO dto, final List<CategoryModel> lsCategories){
        final ProductModel entity = new ProductModel();

        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());

        entity.getCategories().clear(); // limpando conjunto de categorias

        for (CategoryModel categoryModel: lsCategories){
            entity.getCategories().add(categoryModel);
        }

        return entity;
    }

    public ProductModel mapper(Long idProduct, ProductDTO dto, List<CategoryModel> lsCategories) {
        final ProductModel entity = new ProductModel();

        entity.setId(idProduct);
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());

        entity.getCategories().clear(); // limpando conjunto de categorias

        for (CategoryModel categoryModel: lsCategories){
            entity.getCategories().add(categoryModel);
        }

        return entity;
    }
}
