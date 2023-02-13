package com.torhugo.dscatalog.services;

import com.torhugo.dscatalog.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Page<ProductDTO> findAllPaged(Pageable pageRequest);
    public ProductDTO findById(Long id);
    public ProductDTO insert(ProductDTO dto);
    public ProductDTO update(Long id, ProductDTO dto);
    public void delete(Long id);
}
