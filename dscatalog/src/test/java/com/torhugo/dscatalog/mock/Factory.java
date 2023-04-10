package com.torhugo.dscatalog.mock;

import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.model.entities.ProductModel;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class Factory {
    public static ProductModel createProduct(){
        ProductModel product = new ProductModel();
        product.setId(1L);
        product.setName("Test");
        product.setDescription("Test");
        product.setImgUrl("Test");
        product.setPrice(800.00);
        product.setDate(Instant.now());
        product.getCategories().add(createCategory());

        return product;
    }

    public static CategoryModel createCategory(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setName("Test");
        categoryModel.setCreatedAt(Instant.now());
        return categoryModel;
    }

    public static ProductDTO createProductDTO(){
        return new ProductDTO(createProduct(), List.of(createCategory()));
    }
}
