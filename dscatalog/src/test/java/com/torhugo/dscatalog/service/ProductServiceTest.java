package com.torhugo.dscatalog.service;

import com.torhugo.dscatalog.exception.impl.DataBaseException;
import com.torhugo.dscatalog.exception.impl.ResourceNotFoundException;
import com.torhugo.dscatalog.mapper.ProductMapper;
import com.torhugo.dscatalog.mock.Factory;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.model.entities.ProductModel;
import com.torhugo.dscatalog.repository.CategoryRepository;
import com.torhugo.dscatalog.repository.ProductRepository;
import com.torhugo.dscatalog.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper mapper;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PageImpl<ProductModel> page;
    private ProductModel product;
    private Pageable pageable;
    private ProductDTO productDTO;
    private Long existingCategoryId;

    @BeforeEach
    void setUp() throws Exception{
        // cenário
        existingId = 1L;
        existingCategoryId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        pageable = PageRequest.of(0, 10);
        productDTO = Factory.createProductDTO();

        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any()))
                .thenReturn(page);
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(product);
        Mockito.when(repository.findById(existingId))
                .thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId))
                .thenReturn(Optional.empty());
        Mockito.when(categoryRepository.getOne(existingCategoryId))
                .thenReturn(Factory.createCategory());
        Mockito.when(categoryRepository.findById(existingCategoryId))
                .thenReturn(Optional.of(Factory.createCategory()));
        Mockito.when(mapper.mapper(existingId, Factory.createProductDTO(), Factory.createProduct().getCategories()))
                .thenReturn(product);
        Mockito.when(mapper.mapper(Factory.createProductDTO(), Factory.createProduct().getCategories()))
                .thenReturn(product);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnPage(){
        // ação
        Page<ProductDTO> result = service.findAllPaged(pageable);

        // validação
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnProduct(){
        // ação
        final ProductDTO result = service.findById(existingId);

        // validação
        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotExists(){
        // ação
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
        // validação
        Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void updateShouldWhenIdExists(){
        // ação
        ProductDTO update = service.update(existingId, productDTO);
        // validação
        Assertions.assertNotNull(update);
        Mockito.verify(repository, Mockito.times(1)).save(mapper.mapper(existingId, Factory.createProductDTO(), Factory.createProduct().getCategories()));
        Mockito.verify(categoryRepository, Mockito.times(1)).getOne(existingCategoryId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){
        // ação & validação
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists(){
        // ação & validação
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDataBaseExceptionWhenDependentId(){
        // ação & validação
        Assertions.assertThrows(DataBaseException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }
}
