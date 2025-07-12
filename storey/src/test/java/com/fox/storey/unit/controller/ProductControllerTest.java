package com.fox.storey.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.storey.controller.ProductController;
import com.fox.storey.dto.ProductDto;
import com.fox.storey.dto.ProductUpdateDto;
import com.fox.storey.entity.*;

import com.fox.storey.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Slf4j
@AutoConfigureMockMvc
@Import(com.fox.storey.util.SecurityTestConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAddProduct() throws Exception {
        Product input = new Product(1L, "Test Product", "", 100f, null);
        Mockito.when(productService.saveProduct(any(ProductDto.class))).thenReturn(input);

        mockMvc.perform(post("/storey/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0f));
        log.info("Added Product: {}",objectMapper.writeValueAsString(input));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product input = new Product(1L, "Updated Product", "", 150f, null);
        Mockito.when(productService.updateProduct(any(ProductUpdateDto.class))).thenReturn(input);

        mockMvc.perform(post("/storey/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(150.0f));
        log.info("Updated Product: {}",objectMapper.writeValueAsString(input));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long productId = 1L;
        Mockito.doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/storey/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productId)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
        log.info("Deleted Product with ID: {}", productId);
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product(1L, "Test Product", "", 100f, null);
        Mockito.when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/storey/products/getById/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0f));
        log.info("Fetched Product by ID: {}", objectMapper.writeValueAsString(product));
    }
}
