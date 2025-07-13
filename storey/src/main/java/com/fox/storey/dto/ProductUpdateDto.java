package com.fox.storey.dto;

public record ProductUpdateDto(
        Long id,
        String name,
        String description,
        Float price,
        Long categoryId
) {}
