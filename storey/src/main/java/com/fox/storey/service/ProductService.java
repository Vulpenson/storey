package com.fox.storey.service;

import com.fox.storey.repository.ProductRepository;
import com.fox.storey.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


}
