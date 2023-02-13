package com.torhugo.dscatalog.services.impl;

import java.util.Optional;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.mapper.CategoryMapper;
import com.torhugo.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.torhugo.dscatalog.model.dto.CategoryDTO;
import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.repository.CategoryRepository;
import com.torhugo.dscatalog.exception.impl.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CategoryMapper categoryUtils;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		
		Page<CategoryModel> list = repository.findAll(pageRequest);
		return list.map(CategoryDTO::new);
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<CategoryModel> obj = repository.findById(id);
		CategoryModel entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		
		return new CategoryDTO(entity);
	}

	@Transactional
    public CategoryDTO insert(CategoryDTO dto) {
		CategoryModel entity = new CategoryModel();
		entity = categoryUtils.mapper(dto);
		repository.save(entity);

		return new CategoryDTO(entity);
    }

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			CategoryModel entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);

			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e){
			throw new DataBaseException("Integrity violation!");
		}
	}
}
