package com.torhugo.dscatalog.resource;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.torhugo.dscatalog.mock.Factory;
import com.torhugo.dscatalog.model.dto.ProductDTO;
import com.torhugo.dscatalog.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceImpl service;

    private PageImpl<ProductDTO> page;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception{
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));

        when(service.findAllPaged(ArgumentMatchers.any()))
                .thenReturn(page);
    }

//    @Test
//    public void findAllShouldReturnPage() throws Exception {
//        mvc.perform(get("/api/products")).andExpect(MockMvcResultMatchers.status().isOk());
//    }
}
