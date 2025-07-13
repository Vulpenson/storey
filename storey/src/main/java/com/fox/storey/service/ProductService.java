package com.fox.storey.service;

import com.fox.storey.dto.ProductDto;
import com.fox.storey.dto.ProductUpdateDto;
import com.fox.storey.entity.Category;
import com.fox.storey.entity.Product;
import com.fox.storey.entity.User;
import com.fox.storey.repository.CategoryRepository;
import com.fox.storey.repository.ProductRepository;
import com.fox.storey.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Product saveProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        if (productRepository.findByName(product.getName()).isPresent()) {
            log.error("Product with name '{}' already exists", product.getName());
            throw new RuntimeException("Product with name '" + product.getName() + "' already exists");
        }
        if (product.getPrice() <= 0) {
            log.error("Product price must be greater than zero");
            throw new RuntimeException("Product price must be greater than zero");
        }
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(ProductUpdateDto productDto) {
        try {
            Product updatedProduct = convertToEntity(productDto);

            Optional<Product> oldProduct = productRepository.findById(updatedProduct.getId());

            if (oldProduct.isEmpty()) {
                log.error("Product not found for update with ID: {}", updatedProduct.getId());
                throw new RuntimeException("Product not found for update with ID: " + updatedProduct.getId());
            }
            return productRepository.save(updatedProduct);
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

    public Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        if (productDto.categoryId() != null) {
            Optional<Category> category = categoryRepository.findById(productDto.categoryId());
            if (category.isEmpty()) {
                log.error("Category not found with ID: {}", productDto.categoryId());
                throw new RuntimeException("Category not found with ID: " + productDto.categoryId());
            }
            product.setCategory(category.get());
        }
        return product;
    }

    public Product convertToEntity(ProductUpdateDto productDto) {
        Product product = new Product();
        product.setId(productDto.id());
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        if (productDto.categoryId() != null) {
            Optional<Category> category = categoryRepository.findById(productDto.categoryId());
            if (category.isEmpty()) {
                log.error("Category not found with ID: {}", productDto.categoryId());
                throw new RuntimeException("Category not found with ID: " + productDto.categoryId());
            }
            product.setCategory(category.get());
        }
        return product;
    }
}
