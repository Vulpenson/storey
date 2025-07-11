package com.fox.storey.service;

import com.fox.storey.entity.Product;
import com.fox.storey.entity.User;
import com.fox.storey.repository.ProductRepository;
import com.fox.storey.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
       try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                log.error("Product not found with ID: {}", id);
                throw new RuntimeException("Product not found with ID: " + id);
            }
            return product.get();
        } catch (Exception e) {
            log.error("Error fetching product by ID: {}", e.getMessage());
            throw new RuntimeException("Error fetching product by ID: " + e.getMessage());
        }
    }

    public Product getProductByName(String name) {
        try {
            Optional<Product> product = productRepository.findByName(name);
            if (product.isEmpty()) {
                log.error("Product not found with name: {}", name);
                throw new RuntimeException("Product not found with name: " + name);
            }
            return product.get();
        } catch (Exception e) {
            log.error("Error fetching product by name: {}", e.getMessage());
            throw new RuntimeException("Error fetching product by name: " + e.getMessage());
        }
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        try {
            if (product.getId() == null || !productRepository.existsById(product.getId())) {
                log.error("Product not found for update with ID: {}", product.getId());
                throw new RuntimeException("Product not found for update with ID: " + product.getId());
            }
            return productRepository.save(product);
        } catch (Exception e) {
            log.error("Error checking product existence for update: {}", e.getMessage());
            throw new RuntimeException("Error checking product existence for update: " + e.getMessage());
        }
    }

    public void deleteProduct(Long productId) {
        try {
            if (!productRepository.existsById(productId)) {
                log.error("Product not found for deletion with ID: {}", productId);
                throw new RuntimeException("Product not found for deletion with ID: " + productId);
            }
            productRepository.deleteById(productId);
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            throw new RuntimeException("Error deleting product: " + e.getMessage());
        }
    }

    public Product changeProductPrice(Long productId, float newPrice) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                log.error("Product not found with ID: {}", productId);
                throw new RuntimeException("Product not found with ID: " + productId);
            }
            Product existingProduct = product.get();
            existingProduct.setPrice(newPrice);
            return productRepository.save(existingProduct);
        } catch (Exception e) {
            log.error("Error changing product price: {}", e.getMessage());
            throw new RuntimeException("Error changing product price: " + e.getMessage());
        }
    }

    public void addProductToFavorites(Long productId, Long userId) {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                log.error("Product not found with ID: {}", productId);
                throw new RuntimeException("Product not found with ID: " + productId);
            }
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                log.error("User not found with ID: {}", userId);
                throw new RuntimeException("User not found with ID: " + userId);
            }
            User existingUser = user.get();
            Product existingProduct = product.get();

            // Check if the product is already in the user's favorites
            if (existingUser.getFavoriteProducts().contains(existingProduct)) {
                log.warn("Product ID: {} is already in user ID: {} favorites", productId, userId);
                throw new RuntimeException("Product is already in favorites");
            }
            existingUser.getFavoriteProducts().add(existingProduct);
            userRepository.save(existingUser);
        } catch (Exception e) {
            log.error("Error adding product to favorites: {}", e.getMessage());
            throw new RuntimeException("Error adding product to favorites: " + e.getMessage());
        }
    }
}
