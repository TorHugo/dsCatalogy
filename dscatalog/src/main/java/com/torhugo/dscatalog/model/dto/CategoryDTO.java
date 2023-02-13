package com.torhugo.dscatalog.model.dto;

import com.torhugo.dscatalog.model.entities.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CategoryDTO {


	private Long id;
	private String name;
	
	public CategoryDTO(CategoryModel entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
