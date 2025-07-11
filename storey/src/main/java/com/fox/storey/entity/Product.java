package com.fox.storey.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "product")
@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "product_name")
    private String name;

    @Column(name = "product_description")
    private String description;

    @Column(nullable = false, name = "product_price")
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
