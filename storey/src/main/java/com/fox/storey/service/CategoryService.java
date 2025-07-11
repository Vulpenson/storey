package com.fox.storey.service;

import com.fox.storey.entity.Category;
import com.fox.storey.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category addCategory(String categoryName) {
        try {
            Category category = new Category();
            if (categoryRepository.findByName(categoryName).isPresent()) {
                log.error("Category with name '{}' already exists", categoryName);
                throw new RuntimeException("Category with name '" + categoryName + "' already exists");
            }
            category.setName(categoryName);
            return categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Error adding category: {}", e.getMessage());
            throw new RuntimeException("Error adding category: " + e.getMessage());
        }
    }

    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all categories: {}", e.getMessage());
            throw new RuntimeException("Error fetching all categories: " + e.getMessage());
        }
    }

    public Category getCategoryById(Long id) {
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
        } catch (Exception e) {
            log.error("Error fetching category by ID: {}", e.getMessage());
            throw new RuntimeException("Error fetching category by ID: " + e.getMessage());
        }
    }

    public void deleteCategory(Long id) {
        try {
            if (!categoryRepository.existsById(id)) {
                log.error("Category not found with ID: {}", id);
                throw new RuntimeException("Category not found with ID: " + id);
            }
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting category: {}", e.getMessage());
            throw new RuntimeException("Error deleting category: " + e.getMessage());
        }
    }
}
