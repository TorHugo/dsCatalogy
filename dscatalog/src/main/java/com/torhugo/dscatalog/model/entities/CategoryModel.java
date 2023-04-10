package com.torhugo.dscatalog.model.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "tb_category")
@Data
public class CategoryModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;

	@ManyToMany(mappedBy = "categories")
	private List<ProductModel> products = new ArrayList<>();

	@PrePersist
	public void prePersist(){
		createdAt = Instant.now();
	}
	@PreUpdate
	public void preUpdate(){
		updatedAt = Instant.now();
	}
}
