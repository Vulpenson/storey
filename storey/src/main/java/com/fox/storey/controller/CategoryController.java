package com.fox.storey.controller;

import com.fox.storey.entity.Category;
import com.fox.storey.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storey/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@NotNull @RequestBody String categoryName) {
        log.info("Adding new category: {}", categoryName);
        Category category = categoryService.addCategory(categoryName);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Category> getCategoryById(@NotNull @PathVariable Long id) {
        log.info("Fetching category with ID: {}", id);
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category with ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
