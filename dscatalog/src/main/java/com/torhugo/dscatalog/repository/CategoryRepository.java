package com.torhugo.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.torhugo.dscatalog.model.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
