package com.torhugo.dscatalog.services;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
    public Page<CategoryDTO> findAllPaged(PageRequest pageRequest);
    public CategoryDTO findById(Long id);
    public CategoryDTO insert(CategoryDTO dto);
    public CategoryDTO update(Long id, CategoryDTO dto);
    public void delete(Long id);
}
