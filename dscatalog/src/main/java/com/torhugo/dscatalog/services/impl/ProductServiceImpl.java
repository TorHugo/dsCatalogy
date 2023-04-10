package com.torhugo.dscatalog.services.impl;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.exception.impl.ResourceNotFoundException;
import com.torhugo.dscatalog.mapper.ProductMapper;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.CategoryModel;
import com.torhugo.dscatalog.model.entities.ProductModel;
import com.torhugo.dscatalog.repository.CategoryRepository;
import com.torhugo.dscatalog.repository.ProductRepository;
import com.torhugo.dscatalog.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductMapper productUtils;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(final Pageable pageRequest){
		log.info("1. Searching products in the database by page {} and size {}.", pageRequest.getPageNumber(), pageRequest.getPageSize());
		Page<ProductModel> list = repository.findAll(pageRequest);

		return list.map(ProductDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(final Long idProduct) {
		log.info("1. Searching product in the database by category id {}", idProduct);
		ProductModel entity = findByIdProduct(idProduct);
		
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
    public ProductDTO insert(final ProductDTO dto) {
		List<CategoryModel> lsCategories = new ArrayList<>();

		log.info("1. Searching categories in the database.");
		dto.getLsCategories().forEach(category -> {
			CategoryModel categoryModel =
					categoryRepository.findById(category.getId()).orElseThrow(() -> new DataBaseException("Category not found! {}"));
			lsCategories.add(categoryModel);
		});

		log.info("2. Mapping the product.");
		ProductModel entity = productUtils.mapper(dto, lsCategories);

		log.info("3. Saving category in the database.");
		repository.save(entity);

		return new ProductDTO(entity, lsCategories);
    }

	@Transactional
	public ProductDTO update(final Long idProduct, final ProductDTO dto) {
		try {
			List<CategoryModel> lsCategories = new ArrayList<>();

			log.info("1. Searching categories in the database.");
			dto.getLsCategories().forEach(category -> {
				CategoryModel categoryModel =
						categoryRepository.getOne(category.getId());
				lsCategories.add(categoryModel);
			});

			log.info("2. Mapping product.");
			ProductModel productMapper =
					repository.save(productUtils.mapper(idProduct, dto, lsCategories));

			log.info("3. Saving product in the database.");
			return new ProductDTO(productMapper, lsCategories);
		} catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found: " + idProduct);
		}
	}

	public void delete(Long idProduct) {
		try {
			log.info("1. Deleting product from database.");
			repository.deleteById(idProduct);
		} catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found: " + idProduct);
		} catch (DataIntegrityViolationException e){
			throw new DataBaseException("Integrity violation!");
		}
	}

	private ProductModel findByIdProduct(final Long idProduct){
		return repository.findById(idProduct).orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
	}
}
