package com.torhugo.dscatalog.repository;

import com.torhugo.dscatalog.mock.Factory;
import com.torhugo.dscatalog.model.entities.ProductModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{
        // cenário
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        // ação
        productRepository.deleteById(existingId);
        Optional<ProductModel> result = productRepository.findById(existingId);

        // verificação
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdNotExists(){
        // ação & verificação
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            productRepository.deleteById(nonExistingId);
        });
    }

    @Test
    public void savePersistWithAutoIncrementWhenIdIsNull(){
        // cenário
        ProductModel model = Factory.createProduct();
        model.setId(null);

        // ação
        productRepository.save(model);

        // verificação
        Assertions.assertNotNull(model.getId());
        Assertions.assertEquals(countTotalProducts + 1, model.getId());
    }

    @Test
    public void findByIdWithIdExists(){
        // ação
        Optional<ProductModel> result = productRepository.findById(existingId);

        // verificação
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdWithIdNonExists(){
        // ação
        Optional<ProductModel> result = productRepository.findById(nonExistingId);

        // verificação
        Assertions.assertFalse(result.isPresent());
    }
}
