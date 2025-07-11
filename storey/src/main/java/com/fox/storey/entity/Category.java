package com.fox.storey.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "category")
@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "category_name")
    private String name;

    @Column(name = "category_description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
