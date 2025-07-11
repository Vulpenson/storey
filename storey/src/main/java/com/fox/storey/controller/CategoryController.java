package com.fox.storey.controller;

import com.fox.storey.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storey/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
}
