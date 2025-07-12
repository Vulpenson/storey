package com.fox.storey.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.storey.dto.ProductDto;
import com.fox.storey.dto.ProductUpdateDto;
import com.fox.storey.entity.AuthRequest;
import com.fox.storey.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@ActiveProfiles("test")
public class ProductsIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    public void setup() throws Exception {
        log.info("Setting up ProductsIntegrationTest...");
        AuthRequest authRequest = new AuthRequest("test@test.com", "admin");

        jwtToken = mockMvc.perform(post("/auth/generateToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andReturn().getResponse().getContentAsString();
        jwtToken = "Bearer " + jwtToken;
    }

    @Test
    public void testAddProduct() throws Exception {
        ProductDto testProduct = new ProductDto();

        testProduct.setName("Test Product");
        testProduct.setDescription("This is a test product");
        testProduct.setPrice(100.0f);
        testProduct.setCategoryId(1L);

        mockMvc.perform(post("/storey/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0f))
                .andDo(result -> log.info("Added Product: {}", result.getResponse().getContentAsString()));
    }

    @Test
    public void testGetProductById() throws Exception {
        Long productId = 1L; // Assuming a product with ID 1 exists

        mockMvc.perform(get("/storey/products/getById/{id}", productId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(productId))
                .andDo(result -> log.info("Fetched Product by ID: {}", result.getResponse().getContentAsString()));
    }

    @Test
    public void testGetProductByName() throws Exception {
        String productName = "QuietBeats";

        mockMvc.perform(get("/storey/products/getByName/{name}", productName)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(productName))
                .andDo(result -> log.info("Fetched Product by Name: {}", result.getResponse().getContentAsString()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductUpdateDto updatedProduct = ProductUpdateDto.builder()
                .id(1L)
                .name("Updated Test Product")
                .description("This is a test product")
                .price(100.0f)
                .categoryId(1L)
                .build();


        mockMvc.perform(post("/storey/products/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Test Product"))
                .andExpect(jsonPath("$.price").value(100.0f))
                .andDo(result -> log.info("Updated Product: {}", result.getResponse().getContentAsString()));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L; // Assuming a product with ID 1 exists

        mockMvc.perform(delete("/storey/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", jwtToken)
                        .content(objectMapper.writeValueAsString(productId)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"))
                .andDo(result -> log.info("Deleted Product with ID: {}", productId));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/storey/products/all").header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

        assertNotNull(productService.getAllProducts(), "Product list should not be null");
    }
}
