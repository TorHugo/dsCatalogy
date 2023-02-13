package com.torhugo.dscatalog.model.dto;

import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.model.entities.ProductModel;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;
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
