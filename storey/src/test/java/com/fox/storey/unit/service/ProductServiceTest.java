package com.fox.storey.unit.service;


import com.fox.storey.dto.ProductDto;
import com.fox.storey.entity.Category;
import com.fox.storey.entity.Product;
import com.fox.storey.repository.CategoryRepository;
import com.fox.storey.repository.ProductRepository;
import com.fox.storey.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;

    private ProductDto testProductDto;

    private Category testCategory;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        log.info("Starting Test");
        mocks = MockitoAnnotations.openMocks(this);

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Apple");
        testProduct.setDescription("Juicy red apple");
        testProduct.setPrice(0.5F);

        testProductDto = new ProductDto();
        testProductDto.setName("Apple");
        testProductDto.setDescription("Juicy red apple");
        testProductDto.setPrice(0.5F);
        testProductDto.setCategoryId(1L);

        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Fruits");

        testProduct.setCategory(testCategory);
    }

    @AfterEach
    void tearDown() {
        if (mocks != null) {
            try {
                mocks.close();
            } catch (Exception e) {
                log.error("Error closing mocks: {}", e.getMessage());
            }
        }
    }

    @Test
    void shouldReturnAllProducts() {
        Product testProduct2 = new Product();
        testProduct2.setId(2L);
        testProduct2.setName("Banana");
        testProduct2.setDescription("Sweet yellow banana");
        testProduct2.setPrice(0.3F);
        testProduct2.setCategory(testCategory);

        when(productRepository.findAll()).thenReturn(List.of(testProduct, testProduct2));

        List<Product> result = productService.getAllProducts();

        log.info(result.toString());
        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals("Banana", result.get(1).getName());
    }

    @Test
    void shouldReturnProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Product result = productService.getProductById(1L);

        log.info(result.toString());
        assertNotNull(result);
        assertEquals("Apple", result.getName());
    }

    @Test
    void shouldSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

        Product saved = productService.saveProduct(testProductDto);

        log.info(saved.toString());
        assertNotNull(saved);
        assertEquals("Apple", saved.getName());
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        log.info("Product with ID 1 deleted successfully");
        // Assert
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
