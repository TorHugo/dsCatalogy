package com.torhugo.dscatalog.model.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "tb_category")
@Data
public class Category implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;

	@PrePersist
	public void prePersist(){
		createdAt = Instant.now();
	}
	@PreUpdate
	public void preUpdate(){
		updatedAt = Instant.now();
	}
}
