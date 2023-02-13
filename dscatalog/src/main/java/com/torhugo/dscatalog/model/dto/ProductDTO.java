package com.torhugo.dscatalog.model.dto;

import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.model.entities.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;

    private List<CategoryDTO> lsCategories = new ArrayList<>();

    public ProductDTO(ProductModel entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }

    public ProductDTO(ProductModel entity, Set<CategoryModel> categories){
        this(entity);
        categories.forEach(
                category -> this.lsCategories.add(new CategoryDTO(category))
        );
    }
}
