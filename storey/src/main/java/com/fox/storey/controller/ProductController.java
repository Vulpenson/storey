package com.fox.storey.controller;

import com.fox.storey.dto.ProductDto;
import com.fox.storey.dto.ProductUpdateDto;
import com.fox.storey.entity.Product;
import com.fox.storey.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storey/products")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product) {
        log.info("Adding new product: {}", product.getName());
        Product saved = productService.saveProduct(product);
        log.info("Product added successfully: {}", saved.getName());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductUpdateDto product) {
        log.info("Updating product: {}", product.getName());
        Product updated = productService.updateProduct(product);
        log.info("Updated product: {}", updated.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody Long productId) {
        log.info("Deleting product with ID: {}", productId);
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Fetching product with ID: {}", id);
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        log.info("Fetching product with name: {}", name);
        Product product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/changePrice/{id}")
    public ResponseEntity<Product> changeProductPrice(@PathVariable Long id, @RequestBody Float newPrice) {
        log.info("Changing price for product ID: {}", id);
        Product updatedProduct = productService.changeProductPrice(id, newPrice);
        log.info("Updated product price for ID: {} to {}", id, newPrice);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/addFavorite/{productId}/{userId}")
    public ResponseEntity<String> addProductToFavorites(@PathVariable Long productId, @PathVariable Long userId) {
        log.info("Adding product ID: {} to user ID: {} favorites", productId, userId);
        productService.addProductToFavorites(productId, userId);
        return ResponseEntity.ok("Product added to favorites successfully");
    }
}
