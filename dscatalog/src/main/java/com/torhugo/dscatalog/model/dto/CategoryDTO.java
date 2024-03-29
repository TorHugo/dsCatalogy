package com.torhugo.dscatalog.model.dto;

import com.torhugo.dscatalog.model.entities.CategoryModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;
	@NotBlank(message = "Requeired field!")
	private String name;
	
	public CategoryDTO(CategoryModel entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
