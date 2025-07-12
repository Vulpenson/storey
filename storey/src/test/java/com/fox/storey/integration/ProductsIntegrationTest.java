package com.fox.storey.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

        log.info("All products: {}", objectMapper.writeValueAsString(productService.getAllProducts()));

        jwtToken = mockMvc.perform(post("/auth/generate-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andReturn().getResponse().getContentAsString();
        jwtToken = "Bearer " + objectMapper.readTree(jwtToken).get("token").asText();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/storey/products/all").header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(result -> {
                    log.info(result.getResponse().getContentAsString());
                });

        assertNotNull(productService.getAllProducts(), "Product list should not be null");
    }
}
