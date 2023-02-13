package com.torhugo.dscatalog.repository;

import com.torhugo.dscatalog.model.entities.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
