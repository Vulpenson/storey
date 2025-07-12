package com.fox.storey.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Long categoryId;
}
