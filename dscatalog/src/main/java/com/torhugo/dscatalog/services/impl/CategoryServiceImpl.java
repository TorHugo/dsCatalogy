package com.torhugo.dscatalog.services.impl;

import java.util.Optional;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.mapper.CategoryMapper;
import com.torhugo.dscatalog.services.CategoryService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CategoryMapper categoryUtils;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(final PageRequest pageRequest){
		log.info("1. Searching categories in the database by page {} and size {}.", pageRequest.getPageNumber(), pageRequest.getPageSize());
		Page<CategoryModel> list = repository.findAll(pageRequest);
		return list.map(CategoryDTO::new);
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(final Long idCategory) {
		log.info("1. Searching category in the database by category id {}", idCategory);
		CategoryModel recuperedCategory = findByIdCategory(idCategory);
		
		return new CategoryDTO(recuperedCategory);
	}

	@Transactional
    public CategoryDTO insert(final CategoryDTO dto) {
		log.info("1. Mapping category.");
		CategoryModel entity = categoryUtils.mapper(dto);
		log.info("2. Saving category in the database.");
		repository.save(entity);

		return new CategoryDTO(entity);
    }

	@Transactional
	public CategoryDTO update(final Long idCategory, final CategoryDTO dto) {
		try {
			log.info("1. Searching category in the database by category id {}", idCategory);
			CategoryModel entity = findByIdCategory(idCategory);
			log.info("2. Mapping category.");
			entity = categoryUtils.mapper(dto);
			log.info("3. Saving category in the database.");
			repository.save(entity);

			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found: " + idCategory);
		}
	}

	public void delete(Long id) {
		try {
			log.info("1. Deleting category from database.");
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e){
			throw new DataBaseException("Integrity violation!");
		}
	}

	private CategoryModel findByIdCategory(final Long idCategory){
		return repository.findById(idCategory).orElseThrow(() -> new DataBaseException("Entity not found."));
	}
}
