package com.fox.storey.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductUpdateDto {
    private Long id;
    private String name;
    private String description;
    private Float price;
    private Long categoryId;
}
