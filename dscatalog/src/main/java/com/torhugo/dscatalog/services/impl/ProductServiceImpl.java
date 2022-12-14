package com.torhugo.dscatalog.services.impl;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.exception.impl.ResourceNotFoundException;
import com.torhugo.dscatalog.mapper.ProductMapper;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.Product;
import com.torhugo.dscatalog.repository.ProductRepository;
import com.torhugo.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductMapper productUtils;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Product> list = repository.findAll(pageRequest);
		return list.map(ProductDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
    public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		entity = productUtils.mapper(dto);
		repository.save(entity);

		return new ProductDTO(entity);
    }

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);

			return new ProductDTO(entity);
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
