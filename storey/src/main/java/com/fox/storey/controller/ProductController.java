package com.fox.storey.controller;

import com.fox.storey.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storey/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
}
