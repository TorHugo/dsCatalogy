package com.torhugo.dscatalog.services;

import com.torhugo.dscatalog.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> findAll();
    public CategoryDTO findById(Long id);
    public CategoryDTO insert(CategoryDTO dto);
    public CategoryDTO update(Long id, CategoryDTO dto);
    public void delete(Long id);
}
